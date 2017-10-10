package view;

import model.Artifact;
import java.util.ArrayList;


public class ArtifactView {

    public ArtifactView() {

    }

    public String printTable(ArrayList<Artifact> artifacts) {
        String resultTable = "   CATEGORY | NAME | COST\n";

        for (Artifact art : artifacts){
            resultTable += ("> " + art.toString() + "\n");
        }

        return resultTable;
    }
}

