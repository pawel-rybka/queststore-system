package controller;

import dao.ArtifactDao;
import dao.BoughtArtifactDao;
import model.Artifact;
import model.BoughtArtifact;
import model.Student;
import view.ArtifactView;
import view.StudentView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class StudentController {

    private Student student;
    private StudentView studentView;
    private ArtifactDao artifactDao;
    private ArtifactView artifactView;
    private BoughtArtifactDao boughtArtifactDao;


    public StudentController(Student student) {
        this.student = student;
        this.studentView = new StudentView();
        this.artifactDao = new ArtifactDao();
        this.artifactView = new ArtifactView();
        this.boughtArtifactDao = new BoughtArtifactDao();
    }

    public void handleMenu() {

        String userOption = "default";

        while (!userOption.equals("0")) {
            this.studentView.printMenu();
            userOption = studentView.getInput("Choose option.");

            if (userOption.equals("1")) {
                checkBalance();
            } else if (userOption.equals("2")) {
                buyArtifact();
            } else if (userOption.equals("3")) {
                donateForArtifact();
            } else if (userOption.equals("4")) {
                seeMyProfile();
            }
        }
    }


    public void checkBalance() {
        studentView.printMsg("\nYour actual balance: ");
        studentView.printMsg(this.student.getCoins().toString());
    }

    public void buyArtifact() {
        studentView.printMsg("\nYOUR ACCOUNT BALANCE: " + this.student.getCoins().toString());

        ArrayList<Artifact> artifacts = null;

        try {
            artifacts = artifactDao.getArtifacts();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Artifact> availableArtifacts = this.checkAvailableArtifacts(artifacts);

        studentView.printMsg("\nAVAILABLE ARTIFACTS: \n" + artifactView.printTable(availableArtifacts));

        try {
            Artifact choosenArtifact = getArtifactFromList(availableArtifacts);
            LocalDate localDate = LocalDate.now();
            BoughtArtifact boughtArtifact = new BoughtArtifact(this.student.getId(), choosenArtifact.getId(), localDate.toString() );
            boughtArtifactDao.addObject(boughtArtifact);

        } catch (NullPointerException e){
            studentView.printMsg("You didn't choose any artifact");
        } catch (SQLException e){
            studentView.printMsg("Somethong went wrong during buying artifact");
        }
    }


    private ArrayList<Artifact> checkAvailableArtifacts(ArrayList<Artifact> artifacts) {
        ArrayList<Artifact> availableArtifacts = new ArrayList<>();

        for (Artifact artifact : artifacts){
            if (artifact.getPrice() <= this.student.getCoins()){
                availableArtifacts.add(artifact);
            }
        }
        return availableArtifacts;
    }


    private Artifact getArtifactFromList(ArrayList<Artifact> availableArtifacts) throws NullPointerException {
        String artifactName = studentView.getInput("\n Choose the name of artifact to buy: ");

        Artifact choosenArtifact = null;

        for(Artifact artifact : availableArtifacts){
            if(artifact.getName().equals(artifactName)){
                choosenArtifact = artifact;
            }
        }
        return choosenArtifact;
    }

    public void donateForArtifact() {
        System.out.println("\nNOT IMPLEMENTED YET");
    }

    public void seeMyProfile() {
        studentView.printMsg("\nFirst name: " + this.student.getFirstName());
        studentView.printMsg("Last name: " + this.student.getLastName());
        studentView.printMsg("Phone Number: " + this.student.getFirstName());
        studentView.printMsg("Email: " + this.student.getEmail());
        studentView.printMsg("Password: " + this.student.getPassword());
    }
}