using System;
using System.Linq;
using System.Collections.Generic;

namespace Jeroen.Day2
{
    public struct Coordinate
    {
        public readonly int Row;
        public readonly int Column;

        public Coordinate(int row, int column)
        {
            Row = row;
            Column = column;
        }
        public Coordinate Left() => new Coordinate(Row, Column - 1);
        public Coordinate Right() => new Coordinate(Row, Column + 1);
        public Coordinate Up() => new Coordinate(Row - 1, Column);
        public Coordinate Down() => new Coordinate(Row + 1, Column);
        public Coordinate Move(char direction)
        {
            switch (direction)
            {
                case 'U': return Up();
                case 'D': return Down();
                case 'L': return Left();
                case 'R': return Right();
            }
            throw new InvalidOperationException();
        }
    }

    public class Keypad
    {
        char?[,] _keys;
        Coordinate _coordinate;

        public Keypad(char?[,] keys, Coordinate coordinate)
        {
            _keys = keys;
            _coordinate = coordinate;
        }

        public char? Current => _keys[_coordinate.Row, _coordinate.Column];

        public void Move(char direction)
        {
            var next = _coordinate.Move(direction);
            if (
                next.Row >= 0 && next.Row < _keys.GetLength(0)
                && next.Column >= 0 && next.Column < _keys.GetLength(1)
                )
                _coordinate = next;
        }
    }
}