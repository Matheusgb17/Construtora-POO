
# Projeto final de POO: Construtora

O código neste repositório refere-se ao trabalho final da disciplina de *Programação Orientada a Objetos*, ministrada pela professora *Patrícia Aparecida Proença Ávila*.

## Diagrama de classes

Até o dia 11/12/2024, o diagrama de classes deste projeto é o que está a seguir. Uma vez que esse código passará por posteriores modificações até sua versão finalizada, o diagrama de classes também mudará.

![Diagrama de classes até o dia 11/12/2024](assets/classes_11_12_2024.png)

## Como utilizar

Esta seção mostrará como configurar o banco de dados para que a aplicação execute corretamente na máquina.

### Clonando o repositório
Abra o terminal ou a aplicação de Git Bash na sua pasta de preferência e execute o comando ` https://github.com/Matheusgb17/Construtora-POO.git`. Isso irá colocar os arquivos da branch `main` em seu repositório local.
Na sua IDE para programar em Java, simplesmente abra o projeto clonado. Por exemplo, no *Netbeans*, basta fazer `Ctrl + Shift + O` para abrir um novo projeto. Dessa forma, já será possível editar os arquivos do código.

### Configurando o banco de dados
Este projeto é um projeto Java em *Maven*, portanto, não há um arquivo propriamente dito de um conector MySQL, mas sim há sua declaração no arquivo `pom.xml`. Dessa forma, para que o projeto execute corretamente, a chamada do conector deve ser feita antes de rodá-lo. No Netbeans, basta limpar e construir o projeto (*Clean and Build Project*, `Shift + F11`) que o conector será adicionado a partir da internet nele.
#### Criando o banco de dados no MySQL
Abrindo o MySQL Workbench, entre na conexão que você tem disponível e abra um arquivo SQL novo: selecione o arquivo `construtora.sql` dentro da pasta `sql` e execute-o. Pronto! Dessa forma, o banco de dados está pronto para ser manipulado.
#### Conectando o Java ao banco de dados
No arquivo `Conexao.java`, dentro do caminho `src/main/java/construtora/model/dao`, deve-se mudar as linhas a seguir para o usuário e a senha do MySQL da sua máquina.
```
public static final String USER = "";
public static final String PASSWORD = "";
```

Assim, deve estar tudo pronto! Basta executar a aplicação! No *Netbeans*, `F6`!
#### Seeding
Esta aplicação usa um sistema de seeding para facilitar algumas operações possíveis de serem feitas nela. Caso ela seja executada em um ambiente que ainda não possua nenhum dado, dados específicos e aleatórios serão adicionados nela. Os dados específicos são usuários padrão para que se possa acessar as funcionalidades da aplicação em um primeiro momento. São os seguintes:
| Papel         | Login       | Senha     | Acesso                                              |
|---------------|-------------|-----------|-----------------------------------------------------|
| Administrador | 99999999999 | 123456789 | Acesso a todas as funcionalidades da aplicação      |
| Cliente       | 88888888888 | 123456789 | Acesso às funcionalidades referentes ao cliente     |
| Construtor    | 77777777777 | 123456789 | Acesso às funcionalidades referentes ao construtor  |
| Engenheiro    | 66666666666 | 123456789 | Acesso às funcionalidades referentes ao engenheiro  |
| Funcionário   | 55555555555 | 123456789 | Acesso às funcionalidades referentes ao funcionário |

Os dados aleatórios são inseridos caso não haja um administrador padrão cadastrado na aplicação. Se esse for o caso, insere-se 100 registros variando entre todos os papéis: administrador, cliente, construtor, engenheiro e funcionário.