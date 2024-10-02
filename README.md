# 💲Conversor de Moedas💲

Um conversor de moedas simples que permite a conversão entre vários tipos moedas diferentes.

## Índice

- [Sobre](#sobre)
- [Funcionalidades](#funcionalidades)
- [Moedas Suportadas](#moedas-suportadas)
- [Manutenção](#-manutenção)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação](#instalação)
- [Uso](#uso)
- [Contribuição](#contribuição)

## Sobre

Este conversor de moedas foi desenvolvido para facilitar a conversão entre diferentes moedas, permitindo que quem o
utilize realize conversões rápidas e acessíveis.

## Funcionalidades

- Conversão entre bastantes tipos de moedas diferentes. Cada uma regionalizada com sua formatação e símbolo específicos.
- Informação em tempo instantâneo: Os resultados apresentados por esse conversor são informações reais e
  atualizadas frequentemente.

## Moedas Suportadas

As mais de **100** moedas compatíveis com esse conversor podem ser vistas observando o
arquivo [EnumCoin.java](src/enums/EnumCoin.java).

No menu inicial já consta algumas para uso direto:

- REAL
- DOLLAR
- EURO
- LIBRAS ESTERLINAS
- BITCOIN

## Manutenção

- Manutenção simplificada no sentido de adicionar/remover moedas para uso.
- Para modificar as moedas esse conversor irá manipular, basta editar o arquivo
  [EnumCoinToUse.java](src/enums/EnumCoinToUse.java). Tem um exemplo lá comentado de como adicionar mais uma moeda.
- Todas as moedas incluídas devem seguir o padrão de inclusão constante lá, em que se obtém a sigla (que vem do
  arquivo [EnumCoin.java](src/enums/EnumCoin.java)) e região (Locale) de onde a moeda é utilizada no mundo.

## Tecnologias Utilizadas

- Linguagem: Java
- API: [Awesome](https://economia.awesomeapi.com.br/last/)

## Instalação

Antes de rodar a aplicação, você precisa instalar o Java JDK 17 ou superior.
[Link para download do JDK 17.](https://www.oracle.com/br/java/technologies/downloads/#java17)

Instale também o GIT
[Link para instalação do GIT.](https://git-scm.com/downloads)

Siga os passos abaixo para baixar e configurar a aplicação localmente:

1. Abra um terminal/Prompt em uma pasta de sua preferência.
2. Clone esse repositório:
   ```bash
   git clone https://github.com/djairdj/one_conversor_de_moedas.git conversor_de_moedas
   ```
3. **Navegue até o diretório do projeto:**
   ```bash
   cd conversor_de_moedas/
   ```
4. **Abra um editor de código na pasta e execute por ele**

## Uso

Após executar o programa pelo terminal, é só seguir as instruções para selecionar as moedas e inserir o valor a ser
convertido.

## Contribuição

Contribuições são bem-vindas!

Sinta-se à vontade para sugerir melhorias, correções de bugs ou novas funcionalidades.
