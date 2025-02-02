package viste.second;

import ctrlApplicativo.HomepageAllenatoreCtrlApplicativo;

public class HomepageAllenatoreCLI extends GenericaCLI {
    public HomepageAllenatoreCLI() {
        this.pagina = "Homepage Allenatore";
    }

    @Override
    public void start() {
        stampaPagina();
        HomepageAllenatoreCtrlApplicativo homepageAllenatoreCtrlApplicativo = new HomepageAllenatoreCtrlApplicativo();
        System.out.println("Cosa vuoi fare?");
        System.out.println("1. Creazione squadra");
        System.out.println("2. Visualizza le richieste di partecipazione alla squadra");
        System.out.println("3. Creazione allenamento");
        System.out.println("4. Torna al Login");
        int scelta = scanner.nextInt();
        scanner.nextLine();
        boolean ciclo = true;

        while (ciclo) {
            switch (scelta) {
                case 1:
                    if(homepageAllenatoreCtrlApplicativo.esisteSquadra()){
                        System.out.println("L'utente ha già una squadra");
                        scelta = scanner.nextInt();
                    }
                    else{
                        prossimaPagina = CreazioneSquadraCLI.class.getName();
                        ciclo = false;
                    }
                    break;
                case 2:
                    if(!homepageAllenatoreCtrlApplicativo.esisteSquadra()){
                        System.out.println("L'allenatore non ha una squadra");
                        scelta = scanner.nextInt();
                    }
                    else {
                        prossimaPagina = VisualizzaRichiesteCLI.class.getName();
                        ciclo = false;
                    }
                    break;
                case 3:
                    if(!homepageAllenatoreCtrlApplicativo.esisteSquadra()){
                        System.out.println("L'allenatore non ha una squadra");
                        scelta = scanner.nextInt();
                    }
                    else {
                        prossimaPagina = CreazioneAllenamentoCLI.class.getName();
                        ciclo = false;
                    }
                    break;
                case 4:
                    prossimaPagina = LoginCLI.class.getName();
                    ciclo = false;
                    break;
                default:
                    System.out.println("Scelta non valida, riprovare");
                    break;
            }
        }
        spostamento(prossimaPagina);
    }
}
