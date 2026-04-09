package engineering.pattern.abstract_factory;

import engineering.dao.*;

public class InMemoryDAOFactory extends DAOFactory {

    @Override
    public UtenteDAO createUtenteDAO() {
        return new UtenteDAOInMemory();
    }

    @Override
    public AllenamentoDAO createAllenamentoDAO() {
        return new AllenamentoDAOInMemory();
    }

    @Override
    public SquadraDAO createSquadraDAO() {
        return new SquadraDAOInMemory();
    }
}
