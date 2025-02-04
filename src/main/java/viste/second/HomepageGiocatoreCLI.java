package viste.second;


import ctrl_applicativo.HomepageGiocatoreCtrlApplicativo;

public class HomepageGiocatoreCLI extends GenericaCLI {

    public HomepageGiocatoreCLI() {
        this.pagina = "Homepage Giocatore";
    }

    @Override
    public void start() {

        stampaPagina();


        HomepageGiocatoreCtrlApplicativo homepagegiocatorectrlapplicativo = new HomepageGiocatoreCtrlApplicativo();

        boolean continua = true;
        while(continua) {
            System.out.println("Per iscriversi ad una Squadra premere 1");
            System.out.println("Per iscriversi ad un allenamento premere 2");
            System.out.println("Per consultare gli allenamenti a cui sei iscritto premere 3");
            System.out.println("Per tornare al login premere 4");

            int scelta=scanner.nextInt();
            scanner.nextLine();

            switch(scelta) {
                case 1:
                    if(homepagegiocatorectrlapplicativo.isUtenteInSquadra()){
                        System.out.println("Sei già iscritto ad una squadra\n");
                    } else {
                        prossimaPagina = EntraInSquadraCLI.class.getName();
                        continua = false;
                    }
                    break;
                case 2:
                    if (homepagegiocatorectrlapplicativo.isUtenteInSquadra()) {
                        prossimaPagina = IscrivitiAllenamentoCLI.class.getName();
                        continua = false;
                    } else {
                        System.out.println("Devi essere iscritto ad una squadra per iscriverti ad un allenamento\n");
                    }
                    break;
                case 3:
                    if (homepagegiocatorectrlapplicativo.isUtenteInSquadra()) {
                        prossimaPagina = ConsultaAllenamentiCLI.class.getName();
                        ConsultaAllenamentiCLI.setPaginaHome(HomepageGiocatoreCLI.class);
                        continua = false;
                    } else {
                        System.out.println("Devi essere iscritto ad una squadra per iscriverti ad un allenamento\n");
                    }
                    break;
                case 4:
                    continua = false;
                    prossimaPagina = LoginCLI.class.getName();
                    break;
                default:
                    System.out.println("Scelta non valida, inserire un numero tra 1 e 4\n");
                    break;
            }
        }
        spostamento(prossimaPagina);
    }
}
