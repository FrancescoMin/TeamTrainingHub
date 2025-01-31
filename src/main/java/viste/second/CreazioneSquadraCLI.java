package viste.second;

import ctrlApplicativo.CreazioneSquadraCtrlApplicativo;

import java.util.Objects;

public class CreazioneSquadraCLI extends GenericaCLI{

    public CreazioneSquadraCLI() {
        this.pagina = "Creazione Squadra";
    }

    @Override
    public void start(){

        stampaPagina();
        boolean ciclo = true;

        System.out.println("Cosa vuoi fare?");
        System.out.println("1. Inserisci il nome della squadra");
        System.out.println("2. Torna alla homepage allenatore");
        System.out.println("3. Torna al login");

        while(ciclo){
            int scelta = scanner.nextInt();
            switch(scelta){
                case 1:
                    ciclo = false;
                    break;
                case 2:
                    prossimaPagina = HomepageAllenatoreCLI.class.getName();
                    ciclo = false;
                    break;
                case 3:
                    prossimaPagina = LoginCLI.class.getName();
                    ciclo = false;
                    break;
                default:
                    System.out.println("Scelta non valida, riprovare");
                    break;
            }
        }
        if (!Objects.equals(prossimaPagina, "")){
            spostamento(prossimaPagina);
        }

        ciclo=true;
        while(ciclo) {
            System.out.println("Inserisci il nome della squadra");
            String nomeSquadra = scanner.next();

            // Creazione squadra
            CreazioneSquadraCtrlApplicativo creazioneSquadraCtrlApplicativo = new CreazioneSquadraCtrlApplicativo();
            try {
                creazioneSquadraCtrlApplicativo.creazioneSquadra(nomeSquadra);
            }
            catch (Exception e) {
                System.out.println("Errore durante la creazione della squadra: " + e.getMessage());
                System.out.println("Se vuoi tornare alla homepage allenatore premi 1, altrimenti premi un numero qualsiasi");

                int scelta = scanner.nextInt();
                if (scelta == 1) {
                    ciclo = false;
                }
            }
        }

        //ritorno alla homepage allenatore
        prossimaPagina = HomepageAllenatoreCLI.class.getName();
        spostamento(prossimaPagina);
    }
}
