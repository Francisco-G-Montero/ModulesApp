package com.frommetoyou.modulesapp2.presentation.utils

/*
class ObjectAnalyzer(
    private val onQrCodesDetected: (qrCodes: List<Barcode>?) -> Unit
) : ImageAnalysis.Analyzer {

    private fun rotationDegreesToFirebaseRotation(rotationDegrees: Int): Int {
        return when (rotationDegrees) {
            0 -> 0
            90 -> 1
            180 -> 2
            270 -> 3
            else -> throw IllegalArgumentException("Not supported")
        }
    }

    override fun analyze(image: ImageProxy) {
        val rotationDegrees = imageProxy.imageInfo.rotationDegrees
        val image = imageProxy.image
        image?.let {
            val processImage = InputImage.fromMediaImage(image, rotationDegrees)
            objectDetector.process(processImage)
                .addOnSuccessListener { listDetectedObjs ->
                    for (detectedObject in listDetectedObjs) {
                        for (label in detectedObject.labels){
                            if (binding.parentCameraLayout.childCount > 1)
                                binding.parentCameraLayout.removeViewAt(1)
                            val objectLabel = detectedObject.labels.firstOrNull()?.text ?: "Undefined"
                            val elementView = DrawView(
                                context = requireContext(),
                                rect = detectedObject.boundingBox,
                                text = objectLabel
                            )
                            showSnackBar("elemento detectado: $objectLabel")
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
}*/
