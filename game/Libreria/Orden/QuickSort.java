package com.mygdx.game.Libreria.Orden;

import com.mygdx.game.Cubos.Block;

public class QuickSort {

    public static void main (String[] arg) {

    }



    static void swap(Block arr[],int i,int j)
    {
        Block t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }


   static int partition (Block arr[], int l, int h)
    {
        Block x = arr[h];
        int i = (l - 1);

        for (int j = l; j <= h- 1; j++)
        {
            if (arr[j].CompareTo(x))
            {
                i++;
                // swap arr[i] and arr[j]
                swap(arr,i,j);
            }
        }
        // swap arr[i+1] and arr[h]
        swap(arr,i+1,h);
        return (i + 1);
    }

    // Sorts arr[l..h] using iterative QuickSort
    public static void QuickSort(Block arr[], int l, int h)
    {
        // create auxiliary stack
        int stack[] = new int[h-l+1];

        // initialize top of stack
        int top = -1;

        // push initial values in the stack
        stack[++top] = l;
        stack[++top] = h;

        // keep popping elements until stack is not empty
        while (top >= 0)
        {
            // pop h and l
            h = stack[top--];
            l = stack[top--];

            // set pivot element at it's proper position
            int p = partition(arr, l, h);

            // If there are elements on left side of pivot,
            // then push left side to stack
            if ( p-1 > l )
            {
                stack[ ++top ] = l;
                stack[ ++top ] = p - 1;
            }

            // If there are elements on right side of pivot,
            // then push right side to stack
            if ( p+1 < h )
            {
                stack[ ++top ] = p + 1;
                stack[ ++top ] = h;
            }
        }
    }



}
