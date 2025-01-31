package viste.second;


public class HomepageGiocatoreCLI extends GenericaCLI {

    public HomepageGiocatoreCLI() {
        this.pagina = "Homepage Giocatore";
    }

    @Override
    public void start() {

        stampaPagina();
        System.out.println("Per iscriversi ad una Squadra premere 1");
        System.out.println("Per iscriversi ad un Allenamenti premere 2");
        System.out.println("Per tornare al login premere 3");

        int scelta=scanner.nextInt();
        boolean continua = true;
        while(continua) {
            switch(scelta) {
                case 1:
                    continua = false;
                    prossimaPagina = EntraInSquadraCLI.class.getName();
                    break;
                case 2:
                    continua = false;
                    prossimaPagina = ConsultaAllenamentiCLI.class.getName();
                    break;
                case 3:
                    continua = false;
                    prossimaPagina = LoginCLI.class.getName();
                    break;
                default:
                    System.out.println("Scelta non valida, inserire un numero tra 1 e 3");
                    scelta = scanner.nextInt();
                    break;
            }
        }
        spostamento(prossimaPagina);
    }
}
