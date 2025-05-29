package util;

import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;

public class LimitarCaracter {

    public enum TipoEntrada {
        NOME, LOGIN, SENHA, EMAIL, FONE;
    }

    private int qtdCaracteres;
    private TipoEntrada tpEntrada;

    public LimitarCaracter(int qtdCaracteres, TipoEntrada tpEntrada) {
        this.qtdCaracteres = qtdCaracteres;
        this.tpEntrada = tpEntrada;
    }

    public TextFormatter<String> getFormatter() {
        return new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            if (newText.length() > qtdCaracteres) {
                return null;
            }

            String insertedText = change.getText();

            switch (tpEntrada) {
                case NOME:
                    if (!insertedText.matches("[\\p{IsLatin} ]*")) return null;
                    break;

                case EMAIL:
                    if (!insertedText.matches("[\\p{IsLatin}@.\\-_0-9]*")) return null;
                    break;

                case FONE: {
    String foneOldText = change.getControlText();
    String foneInsertedText = change.getText();
    int start = change.getRangeStart();
    int end = change.getRangeEnd();

    StringBuilder updatedRaw = new StringBuilder(foneOldText)
        .replace(start, end, foneInsertedText);

    String digits = updatedRaw.toString().replaceAll("[^0-9]", "");

    if (digits.length() > 11) return null;

    StringBuilder foneFormatted = new StringBuilder();
    int foneLen = digits.length();

    if (foneLen > 0) {
        if (foneLen <= 2) {
            foneFormatted.append(digits);
        } else {
            foneFormatted.append(digits.substring(0, 2)).append(" ");
            if (foneLen > 2 && foneLen <= 7) {
                foneFormatted.append(digits.substring(2));
            } else if (foneLen > 7) {
                foneFormatted.append(digits.substring(2, 7)).append("-").append(digits.substring(7));
            }
        }
    }

    change.setText(foneFormatted.toString());
    change.setRange(0, foneOldText.length());

    int caret = foneFormatted.length();
    change.setCaretPosition(caret);
    change.setAnchor(caret);
    break;
}
            }

            return change;
        });
    }
    public static class VerificarData {

    // Método para validar se a data é válida
    public static boolean validarData(String data) {
        if (data.length() != 10) return false;

        try {
            String day = data.substring(0, 2);
            String month = data.substring(3, 5);
            String year = data.substring(6, 10);

            int d = Integer.parseInt(day);
            int m = Integer.parseInt(month);
            int y = Integer.parseInt(year);

            // Verifica se o mês é válido (1-12)
            if (m < 1 || m > 12) {
                return false;
            }

            // Verifica se o dia é válido para o mês
            if (d < 1 || d > 31) {
                return false;
            }

            // Verificação de dias para cada mês
            if (m == 4 || m == 6 || m == 9 || m == 11) {
                if (d > 30) {
                    return false;
                }
            } else if (m == 2) {
                // Verificação para fevereiro (ano bissexto)
                if ((y % 4 == 0 && (y % 100 != 0 || y % 400 == 0)) && d > 29) {
                    return false;
                } else if (d > 28) {
                    return false;
                }
            }

        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
    public void applyToTextInputControl(TextInputControl textInputControl) {
        textInputControl.setTextFormatter(getFormatter());
    }
}