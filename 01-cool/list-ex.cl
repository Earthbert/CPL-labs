(*
    Laborator COOL.
*)

(*
    Exercițiul 1.

    Implementați funcția fibonacci, utilizând atât varianta recursivă,
    cât și cea iterativă.
*)
class Fibo {
    fibo_rec(n : Int) : Int {
        if n < 2 then n else fibo_rec(n - 1) + fibo_rec(n - 2) fi
    };

    fibo_iter(n : Int) : Int {
        let
            a : Int <- 1,
            b : Int <- 1,
            temp : Int
        in
            {
                while
                    1 < n
                loop
                    {   
                        temp <- a;
                        a <- b;
                        b <- a + temp;
                        n <- n - 1;
                    }
                pool;
                a;
            }
    };
};
    
(*
    Exercițiul 2.

    Pornind de la ierarhia de clase implementată la curs, aferentă listelor
    (găsiți clasele List și Cons mai jos), implementați următoarele funcții
    și testați-le. Este necesară definirea lor în clasa List și supradefinirea
    în clasa Cons.

    * append: întoarce o nouă listă rezultată prin concatenarea listei curente
        (self) cu lista dată ca parametru;
    * reverse: întoarce o nouă listă cu elementele în ordine inversă.

    Observați cerințele de întoarcere a unei *noi* liste. Operațiile
    se realizează nedistructiv, fără a altera listele existente. Amintiți-vă
    cum ați scrie axiomele pentru append și reverse într-un limbaj funcțional.
*)

(*
    Listă omogenă cu elemente de tip Int. Clasa List constituie rădăcina
    ierarhiei de clase reprezentând liste. O variabilă cu tipul static List
    poate desemna atât liste vide, cât și nevide. O instanță cu tipul dinamic
    List desemnează o listă vidă. O instanță cu tipul dinamic Cons denotă
    o listă nevidă.

    Adaptare după arhiva oficială de exemple a limbajului COOL.
*)
class List inherits IO {
    isEmpty() : Bool { true };

    -- 0, deși cod mort, este necesar pentru verificarea tipurilor
    hd() : Int { { abort(); 0; } };

    -- Similar pentru self
    tl() : List { { abort(); self; } };

    cons(h : Int) : Cons {
        new Cons.init(h, self)
    };

    print() : IO { out_string("") };

    append(list : List) : List {
        list
    };

    reverse() : List {
        self
    };

    filter(filter : Filter) : List {
        self
    };

    map(map : Map) : List {
        self
    };
};

(*
    În privința vizibilității, atributele sunt implicit protejate, iar metodele,
    publice.

    Atributele și metodele utilizează spații de nume diferite, motiv pentru care
    hd și tl reprezintă nume atât de atribute, cât și de metode.
*)
class Cons inherits List {
    hd : Int;
    tl : List;

    init(h : Int, t : List) : Cons {
        {
            hd <- h;
            tl <- t;
            self;
        }
    };

    -- Supradefinirea funcțiilor din clasa List
    isEmpty() : Bool { false };

    hd() : Int { hd };

    tl() : List { tl };

    print() : IO {
        {
            out_int(hd);
            out_string(" ");
            -- Mecanismul de dynamic dispatch asigură alegerea implementării
            -- corecte a metodei print.
            tl.print();
        }
    };

    append(list : List) : List {{
        tl.append(list).cons(hd);
    }};

    reverse() : List {{
        tl.reverse().append(new List.cons(hd));
    }};

    filter(filter : Filter) : List {{
        if
            filter.apply(hd) 
        then
            tl.filter(filter).cons(hd)
        else
            tl.filter(filter)
        fi;
    }};

    map(map : Map) : List {{
        tl.map(map).cons(map.apply(hd));
    }};
};

(*
    Exercițiul 3.

    Scopul este implementarea unor mecanisme similare funcționalelor
    map și filter din limbajele funcționale. map aplică o funcție pe fiecare
    element, iar filter reține doar elementele care satisfac o anumită condiție.
    Ambele întorc o nouă listă.

    Definiți clasele schelet Map, respectiv Filter, care vor include unica
    metodă apply, având tipul potrivit în fiecare clasă, și implementare
    de formă.

    Pentru a defini o funcție utilă, care adună 1 la fiecare element al listei,
    definiți o subclasă a lui Map, cu implementarea corectă a metodei apply.

    În final, definiți în cadrul ierarhiei List-Cons o metodă map, care primește
    un parametru de tipul Map.

    Definiți o subclasă a subclasei lui Map de mai sus, care, pe lângă
    funcționalitatea existentă, de incrementare cu 1 a fiecărui element,
    contorizează intern și numărul de elemente prelucrate. Utilizați static
    dispatch pentru apelarea metodei de incrementare, deja definită.

    Repetați pentru clasa Filter, cu o implementare la alegere a metodei apply.
*)

class Map {
    apply(elem : Int) : Int {{
        abort();
        0;
    }};
};

class MapIncrementCounter inherits MapIncrement {
    counter : Int;

    apply(elem : Int) : Int {{
        self@MapIncrementCounter.increment();
        self@MapIncrement.apply(elem);
    }};

    increment() : Object {
        counter <- counter + 1
    };

    counter() : Int {
        counter
    };
};

class MapIncrement inherits Map {
    apply(elem : Int) : Int {
        elem + 1
    };
};

class Filter {
    apply(elem : Int) : Bool {{
        abort();
        false;
    }};
};

class FilterLessThen inherits Filter {
    apply(elem : Int) : Bool {
        elem <= 2
    };
};

class FilterEven inherits Filter {
    apply(elem : Int) : Bool {
        (elem / 2) * 2 = elem
    };
};

-- Testați în main.
class Main inherits IO {
    main() : Object {{

        out_string("fibo_rec(8) = ").out_int((new Fibo).fibo_iter(8)).out_string("\n");
        out_string("fibo_iter(6) = ").out_int((new Fibo).fibo_iter(6)).out_string("\n");

        let list : List <- new List.cons(1).cons(2).cons(3),
            list1 : List <- new List.cons(4).cons(5).cons(6),
            temp : List <- list
        in
            {
                -- Afișare utilizând o buclă while. Mecanismul de dynamic
                -- dispatch asigură alegerea implementării corecte a metodei
                -- isEmpty, din clasele List, respectiv Cons.
                while (not temp.isEmpty()) loop
                    {
                        out_int(temp.hd());
                        out_string(" ");
                        temp <- temp.tl();
                    }
                pool;

                out_string("\n");

                -- Afișare utilizând metoda din clasele pe liste.

                out_string("{");
                list.print();
                out_string("} + {");
                list1.print();
                out_string("} = {");
                list.append(list1).print();
                out_string("}\n");

                out_string("{");
                list.print();
                out_string("}.reverse() = {");
                list.reverse().print();
                out_string("}\n");
                
                out_string("{");
                list.print();
                out_string("}.map(new MapIncrement) = {");
                list.map(new MapIncrement).print();
                out_string("}\n");

                out_string("{");
                list.print();
                out_string("}.map(new FilterLessThen) = {");
                list.filter(new FilterLessThen).print();
                out_string("}\n");

                let listAppended : List <- list.append(list1) 
                in  {
                        out_string("{");
                        listAppended.print();
                        out_string("}.map(new FilterEven) = {");
                        listAppended.filter(new FilterEven).print();
                        out_string("}\n");
                    };

                let cim : MapIncrementCounter <- new MapIncrementCounter
                in  {
                        out_string("{");
                        list.print();
                        out_string("}.map(new MapIncrementCounter) = {");
                        list.map(cim).print();
                        out_string("}\n");
                        out_string("Counter = ").out_int(cim.counter()).out_string("\n");
                    };
            };
    }};
};
