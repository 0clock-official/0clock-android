package com.xyz.oclock.framework.ui.signup.stdcard

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.skydoves.bindables.BindingFragment
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.xyz.oclock.R
import com.xyz.oclock.common.onThrottleClick
import com.xyz.oclock.common.toast
import com.xyz.oclock.databinding.FragmentSignUpStdCardBinding


class SignUpStdCardFragment: BindingFragment<FragmentSignUpStdCardBinding>(R.layout.fragment_sign_up_std_card) {

    companion object {
        val TAG: String = SignUpStdCardFragment::class.java.simpleName
        var PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private val viewModel: SignUpStdCardViewModel by viewModels()
    private val permReqLauncher = getPermissionRequestLauncher()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        setListener()
        return binding {
            vm = viewModel
        }.root
    }

    private fun setListener() {
        binding.signUpStdCardLayout.onThrottleClick {
            takePhoto()
        }
    }

    private fun getPermissionRequestLauncher() = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value }
        if (granted) {
            showImagePickerDialog()
        }
    }

    private fun takePhoto() {
        activity?.let {
            if (hasPermissions(activity as Context, PERMISSIONS)) {
                showImagePickerDialog()
            } else {
                permReqLauncher.launch(
                    PERMISSIONS
                )
            }
        }
    }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun showImagePickerDialog() {
        PickImageDialog
            .build(PickSetup())
            .setOnPickResult {
                if (it.error == null) {
                    binding.signUpStdCardImageview.setImageBitmap(it.bitmap)
                    viewModel.stdCardImage = it.bitmap.copy(it.bitmap.config, it.bitmap.isMutable)
                } else {
                    activity?.toast(it.error?.message?: "엥")
                }
            }
            .show(fragmentManager)
    }
}