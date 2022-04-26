import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.common.io.ClassPathResource;

import java.io.IOException;

public class TCN_Model {

    public static void main(String[] args) throws IOException, UnsupportedKerasConfigurationException, InvalidKerasConfigurationException {
        String fullModel = new ClassPathResource("TCN_Model_V2.h5").getFile().getPath();
        MultiLayerNetwork model = KerasModelImport.importKerasSequentialModelAndWeights(fullModel);

    }

}


