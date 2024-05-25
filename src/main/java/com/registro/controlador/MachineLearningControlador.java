package com.registro.controlador;

import com.registro.modelo.Tareas;
import com.registro.servicio.MachineLearningServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MachineLearningControlador {

    @Autowired
    private MachineLearningServicio mlService;

    @GetMapping("/train")
    public String showTrainPage() {
        return "entrenar";
    }

    @PostMapping("/train")
    public String trainModel() {
        try {
            mlService.trainModel();
            return "redirect:/train?success";
        } catch (Exception e) {
            e.printStackTrace();
            return "entrenar";
        }
    }

    @GetMapping("/predict")
    public String showPredictPage(Model model) {
        model.addAttribute("predictionForm", new Tareas());
        try {
            mlService.trainModel();
        }catch ( Exception e){
        	e.printStackTrace();
        }
            
        return "predict";
    }

    @PostMapping("/predict")
    public String predictBestExercises(@ModelAttribute Tareas predictionForm, Model model) {
        try {
            List<Tareas> predictedExercises = mlService.predictBestExercises(
                predictionForm.getContenidoFisico(),
                predictionForm.getContenidoTecnico(),
                predictionForm.getContenidoTactico(),
                predictionForm.getNumJugadores()
            );
            model.addAttribute("predictedExercises", predictedExercises);
            return "predictBestResult"; // Redirect to a page to display the results
        } catch (Exception e) {
            e.printStackTrace();
            return "index"; // Redirect to an error page
        }
    }
}
