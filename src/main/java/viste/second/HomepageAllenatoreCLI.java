package viste.second;

import ctrl_applicativo.HomepageAllenatoreCtrlApplicativo;

public class HomepageAllenatoreCLI extends GenericaCLI {
    public HomepageAllenatoreCLI() {
        this.pagina = "Homepage Allenatore";
    }

    @Override
    public void start() {
        stampaPagina();

        while (continua) {

            System.out.println("Cosa vuoi fare?");
            System.out.println("1. Creazione squadra");
            System.out.println("2. Visualizza le richieste di partecipazione alla squadra");
            System.out.println("3. Creazione allenamento");
            System.out.println("4. Consulta allenamenti");
            System.out.println("5. Modifica allenamenti");
            System.out.println("6. Torna al Login");
            String scelta = scanner.nextLine();


            switch (scelta) {
                case "1":
                    continua=caso1();
                    break;
                case "2":
                    continua=caso2();
                    break;
                case "3":
                    continua = caso3();
                    break;
                case "4":
                    continua = caso4();
                        break;
                case "5":
                    System.out.println("Funzionalità non implementata");
                        break;
                case "6":
                    prossimaPagina = LoginCLI.class.getName();
                    continua = false;
                    break;

                default:
                    System.out.println("Scelta non valida, riprovare");
                    break;
            }
        }
        spostamento(prossimaPagina);
    }

    private boolean caso1(){
        HomepageAllenatoreCtrlApplicativo homepageallenatorectrlapplicativo = new HomepageAllenatoreCtrlApplicativo();

        if(homepageallenatorectrlapplicativo.esisteSquadra()){
            System.out.println("L'utente ha già una squadra");
            return true;
        }
        else{
            prossimaPagina = CreazioneSquadraCLI.class.getName();
            return false;
        }
    }

    private boolean caso2(){
        HomepageAllenatoreCtrlApplicativo homepageallenatorectrlapplicativo = new HomepageAllenatoreCtrlApplicativo();
        if(!homepageallenatorectrlapplicativo.esisteSquadra()){
            System.out.println("L'allenatore non ha una squadra, non ci sono richieste");
            return true;
        }
        else {
            prossimaPagina = VisualizzaRichiesteCLI.class.getName();
            return false;
        }
    }

    private boolean caso3(){
        HomepageAllenatoreCtrlApplicativo homepageallenatorectrlapplicativo = new HomepageAllenatoreCtrlApplicativo();
        if(!homepageallenatorectrlapplicativo.esisteSquadra()){
            System.out.println("L'allenatore non ha una squadra, non puoi creare un allenamento");
            return true;
        }
        else {
            prossimaPagina = CreazioneAllenamentoCLI.class.getName();
            return false;
        }
    }

    private boolean caso4(){
        HomepageAllenatoreCtrlApplicativo homepageallenatorectrlapplicativo = new HomepageAllenatoreCtrlApplicativo();
        if(!homepageallenatorectrlapplicativo.esisteSquadra()){
            System.out.println("L'allenatore non ha una squadra, non puoi consultare gli allenamenti");
            return true;
        }
        else {
            prossimaPagina = ConsultaAllenamentiCLI.class.getName();
            ConsultaAllenamentiCLI.setPaginaHome(HomepageAllenatoreCLI.class.getName());
            return false;
        }
    }
}
