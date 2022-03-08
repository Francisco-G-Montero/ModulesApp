package com.frommetoyou.modulesapp2.presentation.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.frommetoyou.modulesapp2.databinding.FragmentCameraBinding
import com.frommetoyou.modulesapp2.presentation.utils.DrawView
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions

/**
 * Para analizar una imagen con ML Kit y usando un modelo para deteccion o tracking de objetos
 * necesitamos hacer lo siguiente:
 *
 * Descargamos un modelo de tensorflow y lo buscamos para luego asignarlo a un objectDetector
 *
 * Utilizamos un objectDetector en el que van asignadas las opciones de qué modelo de
 * tensorflow vamos a utilizar, la confianza que vamos a manejar, la cantidad de objetos a analizar
 * en un mismo procesamiento de imagen y demás.
 *
 * Configuramos CameraX de manera habitual, pero incorporamos el analyzer donde procesaremos la imagen
 * del preview y en base a ello el objectDetector previamente configurado se encargará de detectar
 * los objetos o elementos según el modelo que hayamos elegido.
 *
 * */
class CameraFragment : Fragment() {
    private lateinit var binding: FragmentCameraBinding
    private lateinit var objectDetector: ObjectDetector
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private var lastObjectLabel = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        val localModel = LocalModel.Builder()
            .setAssetFilePath(OBJECT_DETECTION_PATH)
            .build()
        val customObjectDetectorOptions = CustomObjectDetectorOptions.Builder(localModel)
            .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
            .enableClassification()
            .setClassificationConfidenceThreshold(0.5f)
            .setMaxPerObjectLabelCount(1)
            .build()
        objectDetector = ObjectDetection.getClient(customObjectDetectorOptions)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider = cameraProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentCameraBinding.inflate(layoutInflater)
            return binding.root
        }

        @SuppressLint("UnsafeOptInUsageError")
        private fun bindPreview(cameraProvider: ProcessCameraProvider) {
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(requireActivity())) { imageProxy ->
                val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                val image = imageProxy.image
                image?.let {
                    val processImage = InputImage.fromMediaImage(image, rotationDegrees)
                    objectDetector.process(processImage)
                        .addOnSuccessListener { listDetectedObjs ->
                            for (detectedObject in listDetectedObjs) {
                                for (label in detectedObject.labels) {
                                    if (binding.parentCameraLayout.childCount > 1)
                                        binding.parentCameraLayout.removeViewAt(1)
                                    val objectLabel =
                                        detectedObject.labels.firstOrNull()?.text ?: "Undefined"
                                    val elementView = DrawView(
                                        context = requireContext(),
                                        rect = detectedObject.boundingBox,
                                        text = objectLabel
                                    )
                                    if (lastObjectLabel != objectLabel) {
                                        lastObjectLabel = objectLabel
                                        showSnackBar("Elemento detectado: $objectLabel")
                                    }
                                    binding.parentCameraLayout.addView(elementView)
                                }
                            }
                            imageProxy.close()
                        }.addOnFailureListener {
                            Log.v("MainFragment", "Error ${it.message}")
                            showSnackBar("Error message: ${it.message}")
                            imageProxy.close()
                        }
                }
            }
            cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, imageAnalysis, preview)
        }

        private fun showSnackBar(message: String) {
            Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
                .setDuration(1500)
                .show()
        }
    }
    