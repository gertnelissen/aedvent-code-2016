using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using System.Text;
using System.Threading.Tasks;

namespace Puzzle9b
{
    public class Node
    {
        public List<Node> _childs { get; }

        private string _input;
        private int _nodeRepeat;

        public Node(string input, int nodeRepeat = 1)
        {
            _input = input;
            _nodeRepeat = nodeRepeat;
            _childs = new List<Node>();
            Split(input);
        }

        public BigInteger GetLength()
        {
            if (_childs.Count == 0)
            {
                return _input.Length*_nodeRepeat;
            }
            else
            {
                BigInteger len = 0;
                foreach (var child in _childs)
                {
                    len += child.GetLength();
                }
                return len;
            }
            
        }

        private void Split(string input)
        {
            var ind = input.IndexOf('(');
            var indClosing = input.IndexOf(')');
            var dimension = GetFirstDimension(input);
            if (dimension.Item1 == -1)
            {
                return;
            }


            if (ind > 0)
            {
                var i0 = input.Substring(0, ind);
                _childs.Add(new Node(i0));
            }
            var i1 = input.Substring(indClosing + 1, dimension.Item1);
            var i2 = input.Substring(indClosing + 1 + dimension.Item1);

            if (i1.Length > 0)
            {
                _childs.Add(new Node(i1, dimension.Item2 * _nodeRepeat));
            }
            if (i2.Length > 0)
            {
                _childs.Add(new Node(i2, _nodeRepeat));
            }
        }

        private Tuple<int, int> GetFirstDimension(string input)
        {
            int ind0 = input.IndexOf('(');
            int ind1 = input.IndexOf('x');
            int ind2 = input.IndexOf(')');
            if (ind0 < 0)
            {
                return new Tuple<int, int>(-1,-1);
            }

            return new Tuple<int, int>(
                Int32.Parse(input.Substring(ind0 + 1, ind1 - ind0 - 1)),
                Int32.Parse(input.Substring(ind1 + 1, ind2 - ind1 - 1))
                );
        }
    }
}
