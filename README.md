# Dokumentacja 

## Autorzy
Paulina Przybyłek
Jakub Wiśniewski
Dawid Przybyliński

start: 9 grudnia 2019

## Konspekt projektu

Cele projektu, jego krótka charakterystyka i opis podziału pracy zostały zawarte w pliku Konspekt.pdf. Projekt będzie realizowany zgodnie z zawartymi tam informacjami. Zaleca się zapoznanie z nim.

## Zawartość projektu - opis klas i ich metod

**Klasa csvToDataFrame**    
  Celem tej klasy jest konwertowanie plików csv na Ramki danych naszego autorstwa. 
Do konstruktora przyjmuje ścieżkę do pliku csv oraz separator użyty w pliku. 
Ważne! Csv MUSI posiadać header, jeżeli nie będzie go miało wczytany zostanie pierwszy wiersz danych.
Sprawdzane jest także, czy w żadnej kolumnie podanego pliku csv nie ma pomieszanych wartości numerycznych z wartościami
tekstowymi. Jeżeli zajdzie taka sytuacja program wyrzuci wyjątek.
Obsługuje wyjątki gdy plik jest pusty oraz gdy nie ma rozszerzenia csv.
  
>  ***getColnames()***   
>    zmienia pierwszy wiersz na listę kolumn.
    
>  ***convertToDataFrame()***   
>    Konwertuje linia po lini csv i dodaje do obiektu DataFrame. 

**Klasa DataFrame**   
  Klasa ma za zadanie być naszym głównym narzędziem do posługiwania się danymi. Jednolita, z tymi samymi typami danych w kolumnach będzie świetną i wygodną alternatywą dla R-owych data Frame i Pythonowych Pandasów. 
  
>   ***convertToNumeric()***   
>      Dla każdej kolumny sprawdza czy jest Stringiem, jeżeli tak, to każdy odmienny string zamienia na inną liczbę. Operacje wykonuje na istniejącym DataFrame. Zwraca void. 

>   ***setToPredict(String colname)***  
>      Oznacza kolumnę jako tą, na której będziemy trenować, a później przewidywać.

>   ***getValuesToPredict()***    
>      Zwraca nam kolumnę, którą będziemy w przyszłości przewidywać. W szczególności wykorzystywane przy liczeniu indeksu Gini.

>   ***getColumn(String column)***          
>     Zwraca wartosci w danej kolumnie.

>   ***getDataFrame()***         
>     Zwraca hashmapę, gdzie klucze to nazwy kolumn, a wartościami są wartości w tych kolumnach.

>   ***setColnames(ArrayList<String> colnamesToChange)***         
>     Ustawia nowe nazwy kolumn/zmienia nazwy kolumn w DataFrame przy wykorzystaniu metody setColname(String oldName, String newName).


**Klasa Gini**  
  Indeks Gini odpowiada dystrybucji klas w zbiorze. Gdy przykładowo w danym zbiorze będzie tyle samo elementów o klasach odpowiednio A oraz B to indeks Gini wynosi 0.5. Jeżeli w tym zbiorze będą tylko elementy klasy B, to indeks wynosił będzie 0. Im mniejszy indeks, tym mniejsza entropia w danym zbiorze. Sam indeks posiada wartości od 0 -gdy jest tylko jedna klasa, do 1, gdy jest nieskończenie wiele klas o bardzo małym prawdopodobieństwie.

Indeks Gini określa się wzorem:
![](Giniform-300x68.png)

Do konstruktora potrzebuje ramki danych DataFrame z określoną wcześniej kolumną oraz valuesToPredict

>    ***calculateGiniIndex(ArrayList <Integer\> rowIndexes)***  
>       Dla danych indeksów rzędów (a dokładniej dla kolumny toPredict) liczy indeks Gini. Zwraca liczbę zmiennoprzecinkową od 0 do 1.


**Klasa Node**    
  Klasa odpowiada za tworzenie węzłów przy budowaniu drzewa. Węzły są decyzyjne (Node.Decision) i jako liście (Node.Leaf), stworzone jako klasy wewnętrzne klasy Node. Leaf zawiera jedynie indeksy jakie wpadają do niego oraz dominantę dla tych indeksów, a Decision: indeksy, nazwy kolumn jakie zostały do podziału, nazwę kolumny, której podziału dotyczy węzeł, prawe i lewe dziecko, wartość podziału, głębokość drzewa na danym poziomie oraz dwie listy i wartości indeksu gini dla prawego i lewego dziecka. Obie klasy wewnętrzne posiadają gettery i settery.
  
**Klasa DecisionTree**   
  Klasa odpowiada za tworzenie drzew. Korzysta ona ze wszystkich dotychczasowych napisanych funkcji. Drzewo składa się z głowy (head), węzłów decyzyjnych (Node.Decision) i liści (Node.Leaf). Rośnie rekurencyjnie. Do jego stworzenia potrzeba ramki danych DataFrame z ustawioną kolumną do przewidywania (setToPredict), wybranych indeksów i kolumn oraz maksymalnej głębokości drzewa. 

>    ***findBestSplit(ArrayList<String> colnames, ArrayList<Integer> indexes , Gini gini)***    
>     Metoda znajduje najlepszy split (punkt splitu) ze wszystkich danych kolumn i ich indeksów, zwraca listę zawierającą nazwę kolumny i wartość, która to ma najlepszy podział zmiennych - korzysta z metody split.

>   ***split(String colname,ArrayList<Integer> indexes, Gini gini)***     
>     Dla danej kolumny i indeksów znajduje najlepszy punkt podziału zmiennych - posiadający najlepszy indeks Gini. Zwraca listę zawierającą wartość splitu i index dla niego. Punkt poszukiwany jest co decyl posortowanych wartości

>   ***GrowTree(Node.Decision node)***    
>     Rekursywna metoda tworzenia drzewa.

>   ***CultureTree()***     
>     Tworzy najpierw head drzewa a potem, jeśli spełnione są odpowiednie warunki kolejne węzły drzewa, korzystając z metody GrowTree.

>   ***predict(DataFrame data)***      
>     Zwraca listę dominant - wartości przewidzianych dla każdego wiersza z podanej DataFrame.

>   ***dominant(ArrayList<Integer> vals)***      
>     Oblicza dominantę dla podanych wartości.

>   ***calculatePercentages(ArrayList<Integer> indexes)***      
>     Metoda nie zaimplementowana, być może w wersji 1.2. Jest odpowiedzią na podatną na nierównomierne dystrybucje ***dominant***. W zamyśle liczy udział danej klasy w nodzie, i przechowuje to w ArrayLiście prawdopobieństw. Następnie listy są sumowane po kolumnach, a później indeks o największej wartości jest interesującą nas klasą. 

**Klasa RandomForest**            
  Klasa odpowiada za tworzenie lasu z drzew decyzyjnych. Użytkownik podaje DataFrame, który jest dzielony na dwa zbiory i na ich podstawie budowany jest las oraz testowana jego jakość przewidywania.

>   ***DivisionData()***          
>     Dzieli DataFrame na zbiór testowy i treningowy, wykorzystując przy tym DivisionPergentage, który oznacza procent wierszy dla zbioru treningowego, Wyniki podziału przypisuje do pól trainData i testData.

>   ***train()***        
>     Tworzy las ze zbioru treningowego i ustawia rezultat jako pole trees.

>   ***test()***      
>     Z wykorzystaniem zbioru testowego sprawdza co przewiduje las dla danych wartości w wierszach. Zwraca listę dominant dla każdego wiersza, które sugerują przewidywaną wartość. 

  Powyższe metody są prywatne, aby użytkownik mógł przewidywać za pomocą lasu musi użyć metody forestResults(), która używa tych metod i zwraca wynik z metody test().

>   ***howGoodIsOurForest(ArrayList<Integer> forestResults)***       
>     Umożliwia sprawdzenie jakości przewidywań lasu. Zwraca procent poprawnie przewidzianych wartości ze wszystkich jakie były (sumy).

## Analiza wyników

  Projekt został ukończczony zgodnie z założeniami zawartymi w konspekcie (z małymi zmianami co do kolejności wykonywania zadań i poświęconego czasu na dane punkty - zmiany jakie następiły w trakcie zostały naniesione do konspektu i można je znaleźć w pliku *Kospekt(końcowa wersja).pdf*). Las losowy buduje się szybko i stabilnie, jednak jego przewidywanie jest różne w zależności od zbioru. Na niektórych zbiorach dostajemy bardzo niskie skuteczności, a na niektórych zadziwiająco dobre, które mają przewidywania blisko 100% zgodne z prawidłowymi wynikami. Metoda predict oparta na przewidywaniu z wykorzystaniem dominant okazała się dobrym wyborem dla danych o równych dystrybucjach, wtedy algorytm działa tak jak byśmy chcieli.

  Można założyć, że projekt został wykonany poprawnie i wynieśliśmy z niego ogromną wiedzę na temat lasów losowych. Praca grupowa przebiegała sprawnie, a jako programiści zyskaliśmy nawyk testowania każdej napisanej klasy/metody w celu znajdowania błędów i ulepszania algorytmów. Cel projektu zawarty w konspekcie został osiągnięty, jedynie możemy być niezadowoleni z jakości działania naszego algorytmu w kwestii przewidywania. Poprawianie i ulepszanie algorytmów dotyczących przewidywań, zakładają znalezienie innych metod liczenia indeksów i podziałów - czyli napisanie większości od nowa, a wcale nie oznacza to, że znaleźlibyśmy lepszy sposób. Poprawa przewidywania mogłaby zająć wiele tygodni prac nad zgłębianiem tematyki predykcji. Oznacza to, że projekt jest bardzo złożony i powstanie dobrej bilbioteki wymaga pracy wielomisięcznej, aby dopracować każdy szczegół. Biorąc to pod uwagę, jesteśmy zadowoleni z naszych rezultatów, które osiągnęliśmy w tak krótkim czasie. 
  
