package ctrl_applicativo;

import engineering.bean.AllenamentoBean;
import engineering.dao.AllenamentoDAO;
import engineering.eccezioni.EccezioneAllenamentoInvalido;
import engineering.pattern.Memoria;
import engineering.pattern.abstract_factory.DAOFactory;
import modelli.Allenamento;
import modelli.Utente;

import java.util.ArrayList;
import java.util.List;

public class ConsultaAllenamentiCtrlApplicativo {

    // Metodo per recuperare allenamenti dal sistema
    public List<AllenamentoBean> ottieniAllenamenti() throws EccezioneAllenamentoInvalido {
        try {

            Utente utente = Memoria.getInstance().getUtenteCorrente();

            AllenamentoDAO allenamentoDAO = DAOFactory.getDAOFactory().createAllenamentoDAO();
            List<Allenamento> allenamentiModel = allenamentoDAO.ottieniAllenamentiPerUtente(utente);

            return trasformazione(allenamentiModel);
        }
        catch (EccezioneAllenamentoInvalido e) {
            throw new EccezioneAllenamentoInvalido(e.getMessage());
        }
    }

    private List<AllenamentoBean> trasformazione(List<Allenamento> allenamenti) {
        List<AllenamentoBean> allenamentiBean = new ArrayList<>();
        for(Allenamento a : allenamenti) {
            allenamentiBean.add(new AllenamentoBean(a.getData(), a.getOrarioInizio(), a.getOrarioFine(), a.getDescrizione()));
        }
        return allenamentiBean;
    }
}
