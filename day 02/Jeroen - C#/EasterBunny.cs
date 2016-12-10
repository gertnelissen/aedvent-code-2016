using System;
using System.Linq;
using System.Collections.Generic;

namespace Jeroen.Day2
{
    public class Keypad
    {
        public Keypad(int initial)
        {
            Current = initial;
        }

        public int Current { get; private set; }

        public void Move(char direction)
        {
            // 1 2 3
            // 4 5 6
            // 7 8 9

            switch (direction)
            {
                case 'U' when new[] { 1, 2, 3}.Contains(Current):
                case 'L' when new[] { 1, 4, 7 }.Contains(Current):
                case 'D' when new[] { 7, 8, 9 }.Contains(Current):
                case 'R' when new[] { 3, 6, 9 }.Contains(Current):
                    Current = Current;
                    break;
                case 'U':
                    Current = Current - 3;
                    break;
                case 'D':
                    Current = Current + 3;
                    break;
                case 'L':
                    Current = Current - 1;
                    break;
                case 'R':
                    Current = Current + 1;
                    break;
            }
        }
    }
}