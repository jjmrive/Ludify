/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file and all BarcodeXXX and CameraXXX files in this project edited by
 * Daniell Algar (included due to copyright reason)
 */
package com.jjmrive.ludify.barcode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import com.jjmrive.ludify.modules.CollectionActivity;
import com.jjmrive.ludify.modules.CollectionItemActivity;
import com.jjmrive.ludify.DataHolder;
import com.jjmrive.ludify.modules.FactActivity;
import com.jjmrive.ludify.modules.QuestionActivity;
import com.jjmrive.ludify.R;
import com.jjmrive.ludify.VisitsActivity;
import com.jjmrive.ludify.camera.CameraSource;
import com.jjmrive.ludify.camera.CameraSourcePreview;
import com.jjmrive.ludify.visit.Collection;
import com.jjmrive.ludify.visit.CollectionItem;
import com.jjmrive.ludify.visit.Fact;
import com.jjmrive.ludify.visit.Prize;
import com.jjmrive.ludify.visit.Question;
import com.jjmrive.ludify.visit.Visit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public final class BarcodeCaptureActivity extends Activity
        implements BarcodeTracker.BarcodeGraphicTrackerCallback {

    private static final String TAG = "Barcode-reader";

    // Intent request code to handle updating play services if needed.
    private static final int RC_HANDLE_GMS = 9001;

    // Permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    // Constants used to pass extra data in the intent
    public static final String BarcodeObject = "Barcode";

    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;


    private static final String PARSER_QR = "[#]";
    private static final String PARSER_ARGS = "[|]";
    private static final String PARSER_PRIZES = "[/]";
    private static final String VISIT = "0";
    private static final String QUESTION = "1";
    private static final String FACT = "2";
    private static final String COLLECTION = "3";
    private static final String SUBMIT_VISIT = "end";
    private static final String TYPECOLLECTION = "0";
    private static final String TYPECOLLECTIONITEM = "1";


    /**
     * Initializes the UI and creates the detector pipeline.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.barcode_capture);

        mPreview = (CameraSourcePreview) findViewById(R.id.preview);

        boolean autoFocus = true;
        boolean useFlash = false;

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(autoFocus, useFlash);
        } else {
            requestCameraPermission();
        }
    }

    @Override
    public void onDetectedQrCode(Barcode barcode) {
        if (barcode != null) {
            if (correctQr(barcode.displayValue)) {

                String[] components = barcode.displayValue.split(PARSER_QR);

                int msg_code = Integer.parseInt(components[0]);

                switch (msg_code){
                    case 0:
                        Visit visit = new Visit(Integer.parseInt(components[1]), null, new Date(), null);
                        if (DataHolder.getVisitsList().contains(visit)){
                            if (components[2].equals(SUBMIT_VISIT)){
                                int indx= DataHolder.getVisitsList().indexOf(visit);
                                DataHolder.getVisitsList().get(indx).setStatus(true);
                                DataHolder.saveVisitsList(this.getApplicationContext());
                                Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("SCAN_RESULT", getResources().getText(R.string.ended_visit));
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("SCAN_RESULT", getResources().getText(R.string.already_exists));
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            if (components[2].equals(SUBMIT_VISIT)){
                                Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("SCAN_RESULT", getResources().getText(R.string.uninitialized_visit));
                                startActivity(intent);
                                finish();
                            } else {
                                String[] args = components[2].split(PARSER_ARGS);
                                String[] prizes = args[2].split(PARSER_PRIZES);

                                ArrayList<Prize> prizesList = new ArrayList<>();
                                for (int i = 0; i < prizes.length; i+=2){
                                    Prize prize = new Prize(prizes[i+1], Integer.parseInt(prizes[i]));
                                    prizesList.add(prize);
                                }

                                visit.setName(args[0]);
                                visit.setUrlPhoto(args[1]);
                                visit.addPrizes(prizesList);

                                DataHolder.getVisitsList().add(visit);
                                DataHolder.saveVisitsList(this.getApplicationContext());

                                Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("SCAN_RESULT", args[0] + " " + getResources().getText(R.string.visit_added));
                                startActivity(intent);
                                finish();
                            }
                        }
                        break;
                    case 1:
                        Visit visit2 = new Visit(Integer.parseInt(components[1]), null, new Date(), null);
                        if (DataHolder.getVisitsList().contains(visit2)) {

                            int indx= DataHolder.getVisitsList().indexOf(visit2);

                            String[] args = components[2].split(PARSER_ARGS);
                            ArrayList<String> answers = new ArrayList<>();
                            answers.add(args[2]);
                            answers.add(args[3]);
                            answers.add(args[4]);
                            answers.add(args[5]);
                            Question question = new Question(Integer.parseInt(args[0]), args[1], answers, Integer.parseInt(args[6]), Integer.parseInt(args[7]));

                            if (DataHolder.getVisitsList().get(indx).containsQuestion(question)){
                                Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("SCAN_RESULT", "Question already answered");
                                startActivity(intent);
                                finish();
                            } else {
                                DataHolder.getVisitsList().get(indx).addQuestion(question);
                                DataHolder.saveVisitsList(this.getApplicationContext());
                                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("VISIT_INDEX", indx);
                                intent.putExtra("QUESTION", question);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("SCAN_RESULT", getResources().getText(R.string.uninitialized_visit));
                            startActivity(intent);
                            finish();
                        }

                        break;
                    case 2:

                        Visit visit3 = new Visit(Integer.parseInt(components[1]), null, new Date(), null);
                        if (DataHolder.getVisitsList().contains(visit3)) {
                            int indx= DataHolder.getVisitsList().indexOf(visit3);
                            String[] args = components[2].split(PARSER_ARGS);
                            Fact fact = new Fact(Integer.parseInt(args[0]), args[1], args[2], args[3], Integer.parseInt(args[4]));

                            if (DataHolder.getVisitsList().get(indx).containsFact(fact)){
                                Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("SCAN_RESULT", "Fact already discovered");
                                startActivity(intent);
                                finish();
                            } else {
                                DataHolder.getVisitsList().get(indx).addFact(fact);
                                DataHolder.saveVisitsList(this.getApplicationContext());
                                Intent intent = new Intent(getApplicationContext(), FactActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("VISIT_INDEX", indx);
                                intent.putExtra("FACT", fact);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("SCAN_RESULT", getResources().getText(R.string.uninitialized_visit));
                            startActivity(intent);
                            finish();
                        }

                        break;
                    case 3:

                        Visit visit4 = new Visit(Integer.parseInt(components[1]), null, new Date(), null);
                        if (DataHolder.getVisitsList().contains(visit4)) {
                            int indx= DataHolder.getVisitsList().indexOf(visit4);
                            String[] args = components[2].split(PARSER_ARGS);

                            switch (Integer.parseInt(args[0])){
                                case 0:

                                    Collection collection = new Collection(Integer.parseInt(args[1]), args[2], args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]));

                                    if (DataHolder.getVisitsList().get(indx).containsCollection(collection)){
                                        Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("SCAN_RESULT", "Collection already discovered");
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        DataHolder.getVisitsList().get(indx).addCollection(collection);
                                        DataHolder.saveVisitsList(this.getApplicationContext());
                                        Intent intent = new Intent(getApplicationContext(), CollectionActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("VISIT_INDEX", indx);
                                        intent.putExtra("COLLECTION", collection);
                                        startActivity(intent);
                                        finish();
                                    }
                                    break;
                                case 1:

                                    Collection collection2 = new Collection(Integer.parseInt(args[1]), null, null, 0, 0);

                                    if (DataHolder.getVisitsList().get(indx).containsCollection(collection2)){

                                        CollectionItem item = new CollectionItem(Integer.parseInt(args[2]), Integer.parseInt(args[1]), args[3], args[4], Integer.parseInt(args[5]), Integer.parseInt(args[6]));

                                        if (DataHolder.getVisitsList().get(indx).containsCollectionItem(item)){
                                            Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.putExtra("SCAN_RESULT", "Collection item already discovered");
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            int collectionIndex = DataHolder.getVisitsList().get(indx).getCollections().indexOf(collection2);
                                            DataHolder.getVisitsList().get(indx).addCollectionItem(item);
                                            DataHolder.saveVisitsList(this.getApplicationContext());
                                            Intent intent = new Intent(getApplicationContext(), CollectionItemActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.putExtra("VISIT_INDEX", indx);
                                            intent.putExtra("COLLECTION_INDEX", collectionIndex);
                                            intent.putExtra("COLLECTION_ITEM", item);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("SCAN_RESULT", "Collection not discovered yet");
                                        startActivity(intent);
                                        finish();
                                    }

                                    break;
                                default:
                                    Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("SCAN_RESULT", getResources().getText(R.string.wrong_qr_format));
                                    startActivity(intent);
                                    finish();
                                    break;
                            }

                        } else {
                            Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("SCAN_RESULT", getResources().getText(R.string.uninitialized_visit));
                            startActivity(intent);
                            finish();
                        }
                        break;
                    default:
                        Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("SCAN_RESULT", getResources().getText(R.string.wrong_qr_format));
                        startActivity(intent);
                        finish();
                        break;
                }

            } else {
                Intent intent = new Intent(getApplicationContext(), VisitsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("SCAN_RESULT", getResources().getText(R.string.wrong_qr_format));
                startActivity(intent);
                finish();
            }
        }
    }

    private boolean correctQr(String qr){
        boolean correct = false;
        String[] components = qr.split(PARSER_QR);
        if (components.length == 3){
            correct = true;
        }
        return correct;
    }



    // Handles the requesting of the camera permission.
    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
        }
    }

    /**
     * Creates and starts the camera.
     *
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     */
    @SuppressLint("InlinedApi")
    private void createCameraSource(boolean autoFocus, boolean useFlash) {
        Context context = getApplicationContext();

        // A barcode detector is created to track barcodes.  An associated multi-processor instance
        // is set to receive the barcode detection results, track the barcodes, and maintain
        // graphics for each barcode on screen.  The factory is used by the multi-processor to
        // create a separate tracker instance for each barcode.
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(this);
        barcodeDetector.setProcessor(new MultiProcessor.Builder<>(barcodeFactory).build());

        if (!barcodeDetector.isOperational()) {
            // Note: The first time that an app using the barcode or face API is installed on a
            // device, GMS will download a native libraries to the device in order to do detection.
            // Usually this completes before the app is run for the first time.  But if that
            // download has not yet completed, then the above call will not detect any barcodes
            // and/or faces.
            //
            // isOperational() can be used to check if the required native libraries are currently
            // available.  The detectors will automatically become operational once the library
            // downloads complete on device.
            Log.w(TAG, "Detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error,
                        Toast.LENGTH_LONG).show();
                Log.w(TAG, getString(R.string.low_storage_error));
            }
        }

        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the barcode detector to detect small barcodes
        // at long distances.
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        CameraSource.Builder builder = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(metrics.widthPixels, metrics.heightPixels)
                .setRequestedFps(24.0f);

        // make sure that auto focus is an available option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = builder.setFocusMode(
                    autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null);
        }

        mCameraSource = builder
                .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .build();
    }

    // Restarts the camera
    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    // Stops the camera
    @Override
    protected void onPause() {
        super.onPause();
        if (mPreview != null) {
            mPreview.stop();
        }
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPreview != null) {
            mPreview.release();
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            boolean autoFocus = true;
            boolean useFlash = false;
            createCameraSource(autoFocus, useFlash);
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Multitracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }
}
