package rk.first.saathi.ui.presentation

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.Locale

class OcrImageAnalyzer(
    var viewModel: SaathiViewModel,
    var state : State,
    private val onResults: (String) -> Unit
): ImageAnalysis.Analyzer {
    private var frameSkipCounter = 1
    override fun analyze(image: ImageProxy) {
        if(frameSkipCounter % 60 == 0) {
            //val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
            val inputImage = InputImage.fromBitmap(bitmap, 0)
            val results = runTextRecognition(inputImage,state, viewModel = viewModel)
            //val results = classifier.classify(bitmap, rotationDegrees)
            onResults(results)
        }
        frameSkipCounter++
        image.close()
    }
}

private fun runTextRecognition(image: InputImage,state: State,viewModel: SaathiViewModel):String {
    val options = TextRecognizerOptions.DEFAULT_OPTIONS
    val recognizer = TextRecognition.getClient(options)
    var resultText = ""

    recognizer.process(image)
        .addOnSuccessListener { texts ->
            resultText = texts.text
            //state.value.text = texts.text
            //viewModel.update(texts.text)
            Log.d("State",state.text)

        for (block in texts.textBlocks) {
                val blockText = block.text
                print(blockText)
                val blockCornerPoints = block.cornerPoints
                print(blockCornerPoints)
                val blockFrame = block.boundingBox
                print(blockFrame)
                for (line in block.lines) {
                    val lineText = line.text
                    print(lineText)
                    val lineCornerPoints = line.cornerPoints
                    print(lineCornerPoints)
                    val lineFrame = line.boundingBox
                    print(lineFrame)
                    for (element in line.elements) {
                        val elementText = element.text
                        print(elementText)
                        val elementCornerPoints = element.cornerPoints
                        print(elementCornerPoints)
                        val elementFrame = element.boundingBox
                        print(elementFrame)
                        if(viewModel.importantKeywords.contains(element.text.lowercase(Locale.getDefault()))){
                            viewModel.speak(element.text)
                        }
                    }
                }
            }
        }
        .addOnFailureListener { e -> // Task failed with an exception
            e.printStackTrace()
        }
    return resultText
}

