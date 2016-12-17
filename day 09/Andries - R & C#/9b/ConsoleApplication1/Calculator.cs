using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace Puzzle9b
{
    public class Calculator
    {
        public Calculator()
        {

        }

        public BigInteger Calculate(string input)
        {
            return new Node(input).GetLength();
        }

    }
}
