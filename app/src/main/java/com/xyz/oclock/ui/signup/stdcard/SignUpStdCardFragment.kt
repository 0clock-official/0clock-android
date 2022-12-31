package com.xyz.oclock.ui.signup.stdcard

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import com.skydoves.bindables.BindingFragment
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.xyz.oclock.R
import com.xyz.oclock.common.extensions.hasPermissions
import com.xyz.oclock.common.extensions.onThrottleClick
import com.xyz.oclock.common.extensions.toast
import com.xyz.oclock.databinding.FragmentSignUpStdCardBinding
import com.xyz.oclock.ui.signup.SignUpViewPagerFragmentListener
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import javax.inject.Inject


@AndroidEntryPoint
class SignUpStdCardFragment: BindingFragment<FragmentSignUpStdCardBinding>(R.layout.fragment_sign_up_std_card) {

    @set:Inject
    internal lateinit var viewModelFactory: SignUpStdCardViewModel.AssistedFactory

    @get:VisibleForTesting
    private val viewModel: SignUpStdCardViewModel by viewModels {
        SignUpStdCardViewModel.provideFactory(viewModelFactory, listener)
    }
    private val listener: SignUpViewPagerFragmentListener by lazy {
        parentFragment as SignUpViewPagerFragmentListener
    }

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

    private fun takePhoto() {
        activity?.let {
            if (it.hasPermissions(PERMISSIONS)) {
                showImagePickerDialog()
            } else {
                permReqLauncher.launch(
                    PERMISSIONS
                )
            }
        }
    }

    private fun showImagePickerDialog() {
        PickImageDialog
            .build(PickSetup()
                .setTitle(requireContext().getString(R.string.choose_image))
                .setCameraButtonText(requireContext().getString(R.string.camera))
                .setGalleryButtonText(requireContext().getString(R.string.gallery))
                .setCancelText(requireContext().getString(R.string.cancel))
                .setCameraIcon(R.drawable.ic_camera)
                .setGalleryIcon(R.drawable.ic_garllery)
                .setCancelTextColor(requireContext().getColor(R.color.error))
                .setTitleColor(requireContext().getColor(R.color.main))
                .setProgressTextColor(requireContext().getColor(R.color.main))
                .setButtonTextColor(requireContext().getColor(R.color.main))
                .setButtonOrientation(LinearLayout.HORIZONTAL)
            ).setOnPickResult {
                if (it.error == null) {
                    binding.signUpStdCardImageview.setImageBitmap(it.bitmap)
                    viewModel.stdCardImage = it.bitmap.copy(it.bitmap.config, it.bitmap.isMutable)
                } else {
                    activity?.toast(it.error?.message?: "image picker error")
                }
            }
            .show(fragmentManager)
    }

    private fun getPermissionRequestLauncher() = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value }
        if (granted) {
            showImagePickerDialog()
        }
    }

    companion object {
        val TAG: String = SignUpStdCardFragment::class.java.simpleName
        var PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}