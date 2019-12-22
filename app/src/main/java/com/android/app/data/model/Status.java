package com.android.app.data.model;

public enum Status {
    ATIVO {
        @Override
        public String toString() { return "Ativo"; }
    },
    DESATIVADO {
        @Override
        public String toString() { return "Desativado"; }
    }
}
