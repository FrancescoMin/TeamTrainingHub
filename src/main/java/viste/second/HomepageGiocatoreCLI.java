package viste.second;


import ctrlApplicativo.HomepageAllenatoreCtrlApplicativo;
import ctrlApplicativo.HomepageGiocatoreCtrlApplicativo;

public class HomepageGiocatoreCLI extends GenericaCLI {

    public HomepageGiocatoreCLI() {
        this.pagina = "Homepage Giocatore";
    }

    @Override
    public void start() {

        stampaPagina();


        HomepageGiocatoreCtrlApplicativo homepageGiocatoreCtrlApplicativo = new HomepageGiocatoreCtrlApplicativo();

        boolean continua = true;
        while(continua) {
            System.out.println("Per iscriversi ad una Squadra premere 1");
            System.out.println("Per iscriversi ad un Allenamenti premere 2");
            System.out.println("Per tornare al login premere 3");

            int scelta=scanner.nextInt();
            scanner.nextLine();

            switch(scelta) {
                case 1:
                    if(homepageGiocatoreCtrlApplicativo.isUtenteInSquadra()){
                        System.out.println("Sei già iscritto ad una squadra\n");
                    } else {
                        prossimaPagina = EntraInSquadraCLI.class.getName();
                        continua = false;
                    }
                    break;
                case 2:
                    if (homepageGiocatoreCtrlApplicativo.isUtenteInSquadra()) {
                        prossimaPagina = IscrivitiAllenamentoCLI.class.getName();
                        continua = false;
                    } else {
                        System.out.println("Devi essere iscritto ad una squadra per iscriverti ad un allenamento\n");
                        continua = false;
                    }
                    break;
                case 3:
                    continua = false;
                    prossimaPagina = LoginCLI.class.getName();
                    break;
                default:
                    System.out.println("Scelta non valida, inserire un numero tra 1 e 3\n");
                    scelta = scanner.nextInt();
                    scanner.nextLine();
                    break;
            }
        }
        spostamento(prossimaPagina);
    }
}
