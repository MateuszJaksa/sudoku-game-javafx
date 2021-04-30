module Model {
    requires org.apache.commons.lang3;
    requires java.sql;
    requires java.desktop;
    requires javafx.base;
    exports pl.compprog.serializers;

    opens pl.compprog;
    exports pl.compprog.solvers;
    exports pl.compprog.exceptions;
    exports pl.compprog.models;
    exports pl.compprog;
}