package com.codepath.app6.volunteerbeat.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

public class NetworkUnavailableDialog extends DialogFragment {

	public static void show(FragmentActivity a) {
		DialogFragment fg = new NetworkUnavailableDialog();
		fg.show(a.getSupportFragmentManager(), "NetworkUnavialbleDialog");
	}
	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setMessage("Network Disconnected. Please check network settings.")
		       .setTitle("Network Disconnected");
		// Add the buttons
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User clicked OK button
		           }
		       });

		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		return dialog;
	}

}
