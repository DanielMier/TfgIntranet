package com.registro.servicio;

import com.registro.modelo.Tareas;
import com.registro.repositorio.TareasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

@Service
public class MachineLearningServicio {

    @Autowired
    private TareasRepositorio tareasRepositorio;

    private Classifier classifier;
    private Instances data;

    public void trainModel() throws Exception {
        List<Tareas> tareas = tareasRepositorio.findAll();

        ArrayList<Attribute> attributes = new ArrayList<>();
        List<String> valoresNominalesFisico = new ArrayList<>();
        valoresNominalesFisico.add("Fuerza_Potencia");
        valoresNominalesFisico.add("Resistencia");
        valoresNominalesFisico.add("Velocidad");
        valoresNominalesFisico.add("Integrado");

        List<String> valoresNominalesTecnico = new ArrayList<>();
        valoresNominalesTecnico.add("Conduccion");
        valoresNominalesTecnico.add("Acoso");
        valoresNominalesTecnico.add("Regate");
        valoresNominalesTecnico.add("Cobertura");
        valoresNominalesTecnico.add("Proteccion");
        valoresNominalesTecnico.add("Rechaze");
        valoresNominalesTecnico.add("Control");
        valoresNominalesTecnico.add("Pase");
        valoresNominalesTecnico.add("Apoyo");
        valoresNominalesTecnico.add("Marcage");
        valoresNominalesTecnico.add("Amplitud");
        valoresNominalesTecnico.add("Profundidad");
        valoresNominalesTecnico.add("Desmarque");
        valoresNominalesTecnico.add("Tiro");
        valoresNominalesTecnico.add("Centro");
        valoresNominalesTecnico.add("Remate");
        valoresNominalesTecnico.add("sc"); // Sin contenido

        List<String> valoresNominalesTactico = new ArrayList<>();
        valoresNominalesTactico.add("Ataque");
        valoresNominalesTactico.add("Defensa");
        valoresNominalesTactico.add("Transicion Ofensiva");
        valoresNominalesTactico.add("Transicion Defensiva");
        valoresNominalesTactico.add("sc"); // Sin contenido

        attributes.add(new Attribute("numJugadores"));
        attributes.add(new Attribute("dificultad"));
        attributes.add(new Attribute("contenidoFisico", valoresNominalesFisico));
        attributes.add(new Attribute("contenidoTecnico", valoresNominalesTecnico));
        attributes.add(new Attribute("contenidoTactico", valoresNominalesTactico));
        attributes.add(new Attribute("isBest", List.of("yes", "no")));

        data = new Instances("TrainingTareas", attributes, tareas.size());
        data.setClassIndex(data.numAttributes() - 1);

        for (Tareas tarea : tareas) {
            double[] values = new double[data.numAttributes()];
            values[0] = Double.parseDouble(tarea.getNumJugadores());
            values[1] = data.attribute(1).indexOfValue(tarea.getDificultad());
            values[2] = data.attribute(2).indexOfValue(tarea.getContenidoFisico());
            values[3] = data.attribute(3).indexOfValue(tarea.getContenidoTecnico());
            values[4] = data.attribute(4).indexOfValue(tarea.getContenidoTactico());
            values[5] = data.attribute(5).indexOfValue("yes"); // Suponiendo que 'yes' es la clase para las mejores tareas

            data.add(new DenseInstance(1.0, values));
        }

        classifier = new J48();
        classifier.buildClassifier(data);
    }

    public List<Tareas> predictBestExercises(String contenidoFisico, String contenidoTecnico, String contenidoTactico, String numJugadores) throws Exception {
        Instances testData = new Instances(data, 0);

        double[] values = new double[data.numAttributes()];
        values[0] = Double.parseDouble(numJugadores);
        values[1] = Double.NaN; // Placeholder for difficulty, as it's not used in prediction
        values[2] = data.attribute(2).indexOfValue(contenidoFisico);
        values[3] = data.attribute(3).indexOfValue(contenidoTecnico);
        values[4] = data.attribute(4).indexOfValue(contenidoTactico);
        testData.add(new DenseInstance(1.0, values));

        double predictedClass = classifier.classifyInstance(testData.instance(0));
        String predictedClassValue = data.classAttribute().value((int) predictedClass);
        System.out.println("Predicted Class: " + predictedClassValue);

        if (predictedClassValue.equals("yes")) {
            List<Tareas> topTareas = tareasRepositorio.findTop5(
                contenidoTecnico, contenidoTactico, contenidoFisico
            );
            System.out.println("Top Tareas found: " + topTareas.size());
            if(topTareas.isEmpty()) {
            	List<Tareas> toprelatedTareas = tareasRepositorio.findRelatedTop5(
                        contenidoTecnico, contenidoTactico, contenidoFisico
                    );
                    System.out.println("Top Related Tareas found: " + toprelatedTareas.size());
                    return toprelatedTareas;
            }
            return topTareas;
            
        }
        

        System.out.println("No matching tasks found.");
        return new ArrayList<>();
    }

}
