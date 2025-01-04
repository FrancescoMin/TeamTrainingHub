package engineering.pattern.abstract_factory;

import engineering.dao.*;

public class MySQLDAOFactory extends DAOFactory {

    @Override
    public UtenteDAO createUtenteDAO() {
        return new UtenteDAOMySQL();
    }

    @Override
    public AllenamentoDAO createAllenamentoDAO() {
        return new AllenamentoDAOMySQL();
    }

    @Override
    public SquadraDAO createSquadraDAO() {
        return new SquadraDAOMySQL();
    }

}
