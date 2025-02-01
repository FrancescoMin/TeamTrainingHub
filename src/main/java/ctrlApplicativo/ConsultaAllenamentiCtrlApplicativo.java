package ctrlApplicativo;

import javafx.collections.ObservableList;
import engineering.bean.AllenamentoBean;
import engineering.pattern.observer.CollezioneAllenamenti;

public class ConsultaAllenamentiCtrlApplicativo {

    private CollezioneAllenamenti collezioneAllenamenti;

    public ConsultaAllenamentiCtrlApplicativo() {
        collezioneAllenamenti = CollezioneAllenamenti.getInstance();  // Usa la collezione di allenamenti
    }

    // Metodo per ottenere la lista degli allenamenti (per aggiornare la TableView)
    public ObservableList<AllenamentoBean> getAllenamenti() {
        return collezioneAllenamenti.getState();  // Restituisce gli allenamenti aggiornati
    }
}
