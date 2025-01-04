package engineering.pattern.abstract_factory;

import engineering.dao.*;

public class JsonDAOFactory extends DAOFactory {
    @Override
    public UtenteDAO createUtenteDAO() {
        return new UtenteDAOJSON();
    }

    @Override
    public AllenamentoDAO createAllenamentoDAO() {
        return new AllenamentoDAOJSON();
    }

    @Override
    public SquadraDAO createSquadraDAO() {
        return new SquadraDAOJSON();
    }
}
