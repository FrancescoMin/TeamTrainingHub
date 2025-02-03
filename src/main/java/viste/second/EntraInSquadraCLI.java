package viste.second;

import ctrl_applicativo.EntraInSquadraCtrlApplicativo;
import engineering.eccezioni.EccezioneSquadraInvalida;

public class EntraInSquadraCLI extends GenericaCLI {
    public EntraInSquadraCLI() {
        this.pagina= "Entra In Squadra";
    }

    @Override
    public void start() {

        String nomeSquadra;
        stampaPagina();

        //facciamo un ciclo dove insegnamo il nome di una squadra valida oppure exit per tornare indietro
        do {
            try {
                System.out.println("Per entrare in una squadra, inserisci il nome della squadra. Se si vuole tornare sulla pagina precedente, inserire exit.");

                //prendiamo l'ingresso dell'utente
                nomeSquadra = scanner.nextLine();

                //controlliamo se volgiamo uscire
                if (nomeSquadra.equals("exit")) {
                    prossimaPagina = HomepageGiocatoreCLI.class.getName();
                }
                //se non vogliamo uscire lavoriamo con il nome della squadra
                else {
                    verificaSquadra(nomeSquadra);
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        while(prossimaPagina.isEmpty());
        spostamento(prossimaPagina);
    }

    private void verificaSquadra(String nomeSquadra) throws EccezioneSquadraInvalida {
        try {
            EntraInSquadraCtrlApplicativo entrainsquadractrlapplicativo = new EntraInSquadraCtrlApplicativo();

            //entriamo se la squadra esiste
            if (entrainsquadractrlapplicativo.verificaEsistenzaSquadra(nomeSquadra)) {
                entrainsquadractrlapplicativo.inviaRichiestaAllaSquadra(nomeSquadra);
                System.out.println("Richiesta inviata con successo.");
                this.prossimaPagina = HomepageGiocatoreCLI.class.getName();
            }
            //se la squadra non esiste entriamo qui
            else {
                System.out.println("La squadra non esiste.");
            }
        }
        catch (EccezioneSquadraInvalida e) {
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
    }

}
