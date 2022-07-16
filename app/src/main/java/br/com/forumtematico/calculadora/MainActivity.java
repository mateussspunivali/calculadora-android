package br.com.forumtematico.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.List;

import br.com.forumtematico.calculadora.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private final static int IS_NUMBER = 0;
    private final static int IS_OPERAND = 1;
    private final static int IS_DOT = 2;

    Button buttonNumber0;
    Button buttonNumber1;
    Button buttonNumber2;
    Button buttonNumber3;
    Button buttonNumber4;
    Button buttonNumber5;
    Button buttonNumber6;
    Button buttonNumber7;
    Button buttonNumber8;
    Button buttonNumber9;

    Button buttonBack;
    Button buttonPercent;
    Button buttonDivision;
    Button buttonMultiplication;
    Button buttonSubtraction;
    Button buttonAddition;
    Button buttonEqual;
    Button buttonDot;

    TextView textViewInputNumbers;
    TextView textViewHistory;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buscando os botões da calculadora
        buttonNumber0 = (Button) findViewById(R.id.button_zero);
        buttonNumber1 = (Button) findViewById(R.id.button_one);
        buttonNumber2 = (Button) findViewById(R.id.button_two);
        buttonNumber3 = (Button) findViewById(R.id.button_three);
        buttonNumber4 = (Button) findViewById(R.id.button_four);
        buttonNumber5 = (Button) findViewById(R.id.button_five);
        buttonNumber6 = (Button) findViewById(R.id.button_six);
        buttonNumber7 = (Button) findViewById(R.id.button_seven);
        buttonNumber8 = (Button) findViewById(R.id.button_eight);
        buttonNumber9 = (Button) findViewById(R.id.button_nine);
        buttonBack = (Button) findViewById(R.id.button_back);
        buttonPercent = (Button) findViewById(R.id.button_percent);
        buttonDivision = (Button) findViewById(R.id.button_division);
        buttonMultiplication = (Button) findViewById(R.id.button_multiplication);
        buttonSubtraction = (Button) findViewById(R.id.button_subtraction);
        buttonAddition = (Button) findViewById(R.id.button_addition);
        buttonEqual = (Button) findViewById(R.id.button_equal);
        buttonDot = (Button) findViewById(R.id.button_dot);
        //Buscando textos de exibição
        textViewInputNumbers = (TextView) findViewById(R.id.textView_input_numbers);
        textViewHistory = (TextView) findViewById(R.id.textView_history);

        //Inicializa a classe de banco de dados
        databaseHelper = new DatabaseHelper(this);

        //Início de adição dos listners de botões que adicionam um novo caracter
        buttonNumber0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("0"); }
        });

        buttonNumber1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("1"); }
        });

        buttonNumber2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("2"); }
        });

        buttonNumber3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("3"); }
        });

        buttonNumber4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("4"); }
        });

        buttonNumber5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("5"); }
        });

        buttonNumber6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("6"); }
        });

        buttonNumber7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("7"); }
        });

        buttonNumber8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("8"); }
        });

        buttonNumber9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("9"); }
        });

        buttonPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("%"); }
        });

        buttonDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("/"); }
        });

        buttonMultiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("x"); }
        });

        buttonSubtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("-"); }
        });

        buttonAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("+"); }
        });

        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addCharacter("."); }
        });
        //Fim da adição dos listners de botões que adicionam um novo caracter

        //Adição do listner do botão de remover o ultimo caracter
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { removeLastCharacter(); }
        });

        //Adição do listner do botão de resultado
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { generateResult(); }
        });
    }

    /**
     * Método reponsável por separar os números dos operadores
     * @param string
     * @return
     */
    private List<String> split(String string) {
        //Valida se a string é null caso seja irá retornar null
        if (string == null) {
            return null;
        }

        List<String> result = new ArrayList<String>();

        //Operadores que irão ser procurados
        String[] operators = new String[] { "+", "-", "*", "/" };

        int index = 0;
        while (index < string.length()) {
            //Procura o indice do operado mais próximo
            int minimum = string.length();
            for (String operator : operators) {
                int i = string.indexOf(operator, index);
                if (i > -1)
                    minimum = Math.min(minimum, i);
            }

            //Se um operador for encontrado ele irá separar de string
            if (minimum < string.length()) {
                result.add(string.substring(index, minimum));
                result.add("" + string.charAt(minimum));
                index = minimum + 1;
            } else {
                result.add(string.substring(index));
                break;
            }
        }

        return result;
    }

    /**
     * Método responsável por formatar os número que serão exibidos
     * @param number
     * @return
     */
    private String formatNumber(double number)
    {
        //Caso o número não possua casa decimal após a virgula ele irá ser apresentado como inteiro
        if (number == (long) number) {
            return String.format("%d",(long) number);
        }

        //Caso o contrário ele irá exibir como um decimal com virgula
        return Double.toString(number);
    }

    private void addCharacter(String character)
    {
        //Busca a quantidade de caracteres da operação
        int operationLength = textViewInputNumbers.getText().length();
        //Busca qual é o tipo do caracter
        int characterState = defineCharacter(character);

        //Valida se o tamanho de caracteres da operação é maior que zero
        if (operationLength > 0) {
            //Buscar o ultimo caracter
            String lastCharacter = textViewInputNumbers.getText().charAt(operationLength - 1) + "";
            //Busca qual é o tipo do ultimo caracter
            int lastCharacterState = defineCharacter(lastCharacter);

            //Caso exista um operador e o caracter atual é um operado não é adicionado nada na operação
            if (this.hasOperator() && characterState == IS_OPERAND) {
                return;
            }

            //Caso o caracter atual seja um ponto e o ultimo caracter for um ponto também não é adicionado nada na operação
            if (characterState == IS_DOT && lastCharacterState == IS_DOT) {
                return;
            }

            //Case o número de caracteres for igual a 1, o ultimo caracter for 0 e o caracter atual é um número ou - ele irá substituir o texto
            if (operationLength == 1 && lastCharacter.equals("0") && (characterState == IS_NUMBER || character.equals("-"))) {
                textViewInputNumbers.setText(character);
            } else {
                //Se não irá concatenar o caracter com o texto que já tinha
                textViewInputNumbers.setText(textViewInputNumbers.getText() + character);
            }
        } else {
            //Caso o texto esteja vazio e o caracter a ser adicionar for um número ou - ele irá adicionar o texto
            if (characterState == IS_NUMBER || character.equals("-")) {
                textViewInputNumbers.setText(textViewInputNumbers.getText() + character);
            }
        }
    }

    /**
     * Método para pegar qual é o tipo do caracter
     * @param character
     * @return
     */
    private int defineCharacter(String character)
    {
        //Valida se o caracter é um operador
        if (
            character.equals("+") ||
            character.equals("-") ||
            character.equals("x") ||
            character.equals("/") ||
            character.equals("%")
        ) {
            return IS_OPERAND;
        }

        //Valida se o caracter é um ponto
        if (character.equals("."))
            return IS_DOT;

        //Caso não seja uma das opções acima é um número
        return IS_NUMBER;
    }

    /**
     * Método para validar se existe algum caracter na operação
     * @return
     */
    private boolean hasOperator()
    {
        if (
            textViewInputNumbers.getText().toString().contains("+") ||
                    (textViewInputNumbers.getText().toString().contains("-") &&
                            (
                                (textViewInputNumbers.getText().toString().charAt(0) == '-' && numberOfCharacterSub(textViewInputNumbers.getText().toString()) > 1) ||
                                (textViewInputNumbers.getText().toString().charAt(0) != '-')
                            )
                    )  ||
            textViewInputNumbers.getText().toString().contains("x") ||
            textViewInputNumbers.getText().toString().contains("/") ||
            textViewInputNumbers.getText().toString().contains("%")
        ) {
            return true;
        }

        return false;
    }

    /**
     * Método responsável por contar o número de caracteres de subtração
     * @param text
     * @return
     */
    private int numberOfCharacterSub(String text)
    {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '-') {
                count++;
            }
        }

        return count;
    }

    /**
     * Método responsável por gerar o resultado final
     */
    private void generateResult()
    {
        //Busca a quantidade de caracteres da operaçã
        int operationLength = textViewInputNumbers.getText().length();
        //Valida se o tamanho de caracteres da operação é maior que zero
        if (operationLength > 0) {
            //Buscar o ultimo caracter
            String lastCharacter = textViewInputNumbers.getText().charAt(operationLength - 1) + "";
            //Busca qual é o tipo do ultimo caracter
            int lastCharacterState = defineCharacter(lastCharacter);

            //Valida se o ultimo caracter é um número e se existe algum operador na operação
            if (lastCharacterState == IS_NUMBER && hasOperator()) {
                String textInput = textViewInputNumbers.getText()
                        .toString();

                //Substitui o X da operação por um * para poder realizar o calculo
                textInput = textInput.replace('x', '*');

                //Cria uma operação com a string e calcula o resultado dela
                Expression expression = new ExpressionBuilder(textInput).build();
                double result = expression.evaluate();

                //Separa os números dos operados
                List<String> array = split(textInput);

                Historico h = new Historico();
                //Caso o primeiro caracter for vázio é que o primeiro número é negativo
                if (array.get(0).equals("")) {
                    //Preenche os dados do objeto historico com o número negativo, segundo número e operação
                    h.setPrimeiro_numero(Double.parseDouble(array.get(1) + array.get(2)));
                    h.setOperecao(array.get(3));
                    h.setSegundo_numero(Double.parseDouble((array.get(4))));
                } else {
                    //Preenche os dados do objeto historico com o primeiro número, segundo número e operação
                    h.setPrimeiro_numero(Double.parseDouble(array.get(0)));
                    h.setOperecao(array.get(1));
                    h.setSegundo_numero(Double.parseDouble((array.get(2))));
                }

                //Preenche o resultado do objeto historico
                h.setResultado(result);

                //Cria o historico no banco de dados
                databaseHelper.createHistorico(h);
                //Busca o historico do banco de dados
                h = databaseHelper.getFirstHistorico();

                //Prenche o texto do resultado
                textViewInputNumbers.setText(formatNumber(h.getResultado()));
                //Prenche o texto do histórico
                textViewHistory.setText(formatNumber(h.getPrimeiro_numero()) + h.getOperecao() + formatNumber(h.getSegundo_numero()));
            }
        }
    }

    /**
     * Método responsável por remover o ultimo caracter do texto
     */
    private void removeLastCharacter()
    {
        String textInput = textViewInputNumbers.getText()
                .toString();
        //Verifica se possui pelo menos 1 caracter
        if (textInput.length() > 0) {
            //Remove o ultimo caracter do texto
            textViewInputNumbers.setText(
                    textInput.substring(0, textInput.length() - 1)
            );
        }
    }
}