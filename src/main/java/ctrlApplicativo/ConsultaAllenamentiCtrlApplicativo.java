package ctrlApplicativo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import engineering.bean.AllenamentoBean;
import engineering.pattern.observer.CollezioneAllenamenti;

public class ConsultaAllenamentiCtrlApplicativo {

    private ObservableList<AllenamentoBean> allenamentiList;


    public ConsultaAllenamentiCtrlApplicativo() {

        allenamentiList = FXCollections.observableArrayList(
                new AllenamentoBean("2025-02-01", "10:00", "11:00", "Allenamento di preparazione"),
                new AllenamentoBean("2025-02-02", "15:00", "16:00", "Allenamento di recupero"),
                new AllenamentoBean("2025-02-03", "18:00", "19:00", "Allenamento tecnico")
        );
    }

    // Metodo per ottenere la lista degli allenamenti (per aggiornare la TableView)
    public ObservableList<AllenamentoBean> getAllenamenti() {
        return allenamentiList;  // Restituisce gli allenamenti aggiornati
    }
}
