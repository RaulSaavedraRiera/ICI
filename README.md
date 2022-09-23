# ICI

---MissPacMan---

Te pueden comer
{
    Zona Segura (1/4 mapa1)
    {
        PillMasCercana
    }

    Fantasma Cerca 
    {
        Llegas a PP antes
        {
            Ir a PP
        }

        Llega fantasma antes (a ti o a PP)
        {
            Miras Segundo mas cercano
            {
                Huir de 2
            }
        }
    }

    3 o mas cerca
    {
        Huir de la zona
    }
}

Puedes comer
{
    Fantasma mas cercano dentro de rango de tiempo
    {
        Queda tiempo
        {
            Perseguir
        }

        Poco tiempo 
        {
            Miras segundo mas cercano 
            {
                ShortestPath desde F1 a F2 da tiempo
                {
                    Comes ambos
                }

                Si no
                {
                    Huyes
                }
            }
        }
    }

    No te da tiempo
    {
        Pills
    }
}

---Fantasma---

Fuera de rango
{
    2 a por pacman -> Cada uno un camino distinto

    2 random -> Selecciona zona de mapa cercana a pacman
    {
        Si hay fantasma se separan
    }
}

En rango
{
    Pacman cerca de pill
    {
        Llegas antes a pill -> vas

        No llegas antes -> huyes de pacman
    }

    No cerca
    {
        Ir hacia donde va evitando fantasmas
        {
            Mirar casillas en su direccion, ver si llegan antes
            {
                Interseccion, cada fantasma a una direccion

                Si otro fantasmas coge mismo path, cambia

                Si no quedan path, shortest path
            }
        }
    }
}