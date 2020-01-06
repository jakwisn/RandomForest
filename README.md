# Dokumentacja 

## Autorzy
Paulina Przybyłek
Jakub Wiśniewski
Dawid Przybyliński

start: 9 grudnia 2019

## Konspekt projektu

Cele projektu, jego krótka charakterystyka i opis podziału pracy zostały zawarte w pliku Konspekt.pdf. Projekt będzie realizowany zgodnie z zawartymi tam informacjami. Zaleca się zapoznanie z nim.

### @ToDo
-Zrobić specjalne Exception'y do testów.

-split(colname) : 
  sort()
  znajduje splita, dla którego indeks gini jest minimalny
  zwraca column[gdzie mini_gini + 1] 

-

Dodatkowo można dodać więcej testów klasy DataFrame.
Ważne: Zapytać się czy lepiej, aby klasa z wyjątkiem była publiczna czy zmienić położenie testów, aby mogły korzystać z prywatnych klas.

### Done : 
**Klasa csvToDataFrame**    
  Celem tej klasy jest konwertowanie plików csv na Ramki danych naszego autorstwa. 
  Do konstruktora przyjmuje ścieżkę do pliku csv oraz separator użyty w pliku. 
  Ważne! Csv MUSI posiadać header, jeżeli nie będzie go miało wczytany zostanie pierwszy wiersz danych.
  Sprawdzane jest także, czy w żadnej kolumnie podanego pliku csv nie ma pomieszanych wartości numerycznych z wartościami
  tekstowymi. Jeżeli zajdzie taka sytuacja program wyrzuci wyjątek.
  Obsługuje wyjątki gdy plik jest pusty oraz gdy nie ma rozszerzenia csv.
  
>  ***getColnames()***   
>    zmienia pierwszy wiersz na listę kolumn
    
>  ***convertToDataFrame()***   
>    Konwertuje linia po lini csv i dodaje do obiektu DataFrame. 

**Klasa DataFrame**   
  Klasa ma za zadanie być naszym głównym narzędziem do posługiwania się danymi. Jednolita, z tymi samymi typami danych w kolumnach będzie świetną i wygodną alternatywą dla R-owych data Frame i Pythonowych Pandasów. 
  
>   ***convertToNumeric()***   
>      Dla każdej kolumny sprawdza czy jest Stringiem, jeżeli tak, to każdy odmienny string zamienia na inną liczbę. Operacje wykonuje na istniejącym DataFrame. Zwraca void. 

>   ***setToPredict(String colname)***  
>      Oznacza kolumnę jako tą, na której będziemy trenować, a później przewidywać

>   ***getValuesToPredict()*** 
>      Zwraca nam kolumnę, którą będziemy w przyszłości przewidywać. W szczególności wykorzystywane przy liczeniu indeksu Gini.


**Klasa Gini**  
  Indeks Gini odpowiada dystrybucji klas w zbiorze. Gdy przykładowo w danym zbiorze będzie tyle samo elementów o klasach odpowiednio A oraz B to indeks Gini wynosi 0.5. Jeżeli w tym zbiorze będą tylko elementy klasy B, to indeks wynosił będzie 0. Im mniejszy indeks, tym mniejsza entropia w danym zbiorze. Sam indeks posiada wartości od 0 -gdy jest tylko jedna klasa, do 1, gdy jest nieskończenie wiele klas o bardzo małym prawdopodobieństwie.

Indeks Gini określa się wzorem:
![](Giniform-300x68.png)

Do konstruktora potrzebuje ramki danych DataFrame z określoną wcześniej kolumną oraz valuesToPredict

>    ***calculateGiniIndex(ArrayList <Integer\> rowIndexes)***  
>       Dla danych indeksów rzędów (a dokładniej dla kolumny toPredict) liczy indeks Gini. Zwraca liczbę zmiennoprzecinkową od 0 do 1.






