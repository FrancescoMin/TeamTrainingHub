package viste.second;

import ctrlApplicativo.CreazioneAllenamentoCtrlApplicativo;
import engineering.bean.AllenamentoBean;
import engineering.eccezioni.EccezioneAllenamentoInvalido;

public class CreazioneAllenamentoCLI extends GenericaCLI{
    public CreazioneAllenamentoCLI(){
        this.pagina= "Creazione Allenamento";
    }

    @Override
    public void start(){

        stampaPagina();

        boolean continua = true;
        while(continua){
            try {
                System.out.println("Inserisci il giorno dell'allenamento: ");
                int giorno = scanner.nextInt();
                scanner.nextLine();
                if(giorno < 1 || giorno > 31)       {throw new EccezioneAllenamentoInvalido("Giorno non valido");}


                System.out.println("Inserisci il mese dell'allenamento: ");
                int mese = scanner.nextInt();
                scanner.nextLine();
                if (mese < 1 || mese > 12)          {throw new EccezioneAllenamentoInvalido("Mese non valido");}


                System.out.println("Inserisci l'anno dell'allenamento: ");
                int anno = scanner.nextInt();
                scanner.nextLine();
                if (anno < 2025 )                   {throw new EccezioneAllenamentoInvalido("Anno non valido");}


                System.out.println("Inserisci l'ora di inizio dell'allenamento: ");
                int oraInizio = scanner.nextInt();
                scanner.nextLine();
                if (oraInizio < 0 || oraInizio > 23)        {throw new EccezioneAllenamentoInvalido("Ora di inizio non valida");}

                System.out.println("Inserisci il minuto di inizio dell'allenamento: ");
                int minutoIn = scanner.nextInt();
                scanner.nextLine();
                if (minutoIn < 0 || minutoIn > 59)  {throw new EccezioneAllenamentoInvalido("Minuto di inizio non valido");}

                System.out.println("Inserisci l'ora di fine dell'allenamento: ");
                int oraFine = scanner.nextInt();
                scanner.nextLine();
                if (oraFine < 0 || oraFine > 23)      {throw new EccezioneAllenamentoInvalido("Ora di fine non valida");}

                System.out.println("Inserisci il minuto di fine dell'allenamento: ");
                int minutoFine = scanner.nextInt();
                scanner.nextLine();
                if (minutoFine < 0 || minutoFine > 59){throw new EccezioneAllenamentoInvalido("Minuto di fine non valido");}

                System.out.println("Inserisci la descrizione dell'allenamento, se ne ha una: ");
                String descrizione = scanner.nextLine();

                controlloData(giorno, mese, anno, oraInizio, minutoIn, oraFine, minutoFine);

                String orarioInizio = String.format("%02d-%02d", oraInizio, minutoIn);
                String orarioFine = String.format("%02d-%02d", oraFine, minutoFine);

                //creazione della data con cui verrà salvato l'allenamento
                String data = String.format("%02d-%02d-%04d", giorno, mese, anno);

                //creazione del ben dell'allenamento da far salvare al sistema
                AllenamentoBean allenamentoBean = new AllenamentoBean(data, orarioInizio, orarioFine, descrizione);

                //richiediamo al sistema di salvare il bean dell'allenamento
                CreazioneAllenamentoCtrlApplicativo creazioneAllenamentoCtrlApplicativo = new CreazioneAllenamentoCtrlApplicativo();
                creazioneAllenamentoCtrlApplicativo.creaAllenamento(allenamentoBean);

                System.out.println("Allenamento creato con successo! Adesso se si vuole creare un altro allenamento premere 1, altrimenti premere un tasto qualsiasi");
                int scelta = scanner.nextInt();
                if(scelta != 1) {
                    continua = false;
                    prossimaPagina = HomepageAllenatoreCLI.class.getName();
                }
            }
            catch (EccezioneAllenamentoInvalido e){
                System.out.println("Errore durante la creazione dell'allenamento: " + e.getMessage());
            }
        }
    }
    private void controlloData(int giorno, int mese, int anno, int oraIn, int minutoIn, int oraFin, int minutoFin) throws EccezioneAllenamentoInvalido {
        /*
        if(giorno < 1 || giorno > 31)       {throw new EccezioneAllenamentoInvalido("Giorno non valido");}
        if (mese < 1 || mese > 12)          {throw new EccezioneAllenamentoInvalido("Mese non valido");}
        if (anno < 2025 )                   {throw new EccezioneAllenamentoInvalido("Anno non valido");}
        if (oraIn < 0 || oraIn > 23)        {throw new EccezioneAllenamentoInvalido("Ora di inizio non valida");}
        if (minutoIn < 0 || minutoIn > 59)  {throw new EccezioneAllenamentoInvalido("Minuto di inizio non valido");}
        if (oraFin < 0 || oraFin > 23)      {throw new EccezioneAllenamentoInvalido("Ora di fine non valida");}
        if (minutoFin < 0 || minutoFin > 59){throw new EccezioneAllenamentoInvalido("Minuto di fine non valido");}

         */
    }
}
