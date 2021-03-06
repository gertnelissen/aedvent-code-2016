﻿using System.Diagnostics;
using System.Text;
using Xunit;
using Xunit.Abstractions;

namespace Jeroen.Day2
{

    public class Tests
    {
        private readonly ITestOutputHelper output;

        public Tests(ITestOutputHelper output)
        {
            this.output = output;
        }

        Keypad keypad1 = new Keypad(new char?[,] {
                { '1', '2', '3'},
                { '4', '5', '6'},
                { '7', '8', '9'},
            }, new Coordinate(1, 1));

        string[] testinputs = new[]
        {
            "ULL", "RRDDD", "LURDL", "UUUUD"
        };

        [Fact]
        public void ExamplesPart1()
        {
            var code = GetCode(testinputs, keypad1);
            Assert.Equal("1985", code);
        }


        [Fact]
        public void ExamplesPart2()
        {
            var code = GetCode(testinputs, keypad2);
            Assert.Equal("5DB3", code);
        }

        string[] input = new[]
    {
                    "LUULRUULULLUDUDULDLUDDDLRURUDLRRDRDULRDDULLLRULLLURDDLRDLUUDDRURDDRDDDDRDULULLLLURDDLLRLUUDDDRLRRRDURLDDLRRLDUDRRRDLDLRRDLDLUURRLRULLULRUDRDLRUURLDRDLRLDULLLUDRDDRLURLUUDRLLLDRUUULLUULRUDDUDRDUURRRUDRLDDUURDUURUDRDDLULDDUDUDRRDDULUDULRDRULRLRLURURDULRUULLRDDDDRRUUDDDUUDRLLRUDRLRDLRRLULRLULRUDDULRLLLURLDDRLDDLRRLDRDDDRRLRUDRULUUDUURLDLRRULUDRDULDLLRRURRDDLRRRLULUDUUDDUDDLRDLRDRLRLDUDUDDUDLURRUURDRLRURLURRRLRLRRUDDUDDLUDRLUURUUDUUDDULRRLUUUDRLRLLUR",
                    "LDLLRRLDULDDRDDLULRRRDDUDUDRRLLRUUULRUDLLRRDDRRLDDURUUDLUDRRLDURDDRUDLUDUUDLDLLLDLLLDRLLDLRUULULLUUDULDUUULDDLRUDLLUDLUUULDRLUDRULUUDLDURDLDUULLRDUDRDLURULDLUUUDURLDDRLLDRLRDDDUDRUULLDLUDRRDDLDLUURUDDLDRURRLULUDDURLDRDRDUDDRRULRLDURULULRURDUURRUDRDDRDRLDRDUUDLRULRDDDULRURUDRUUULUUDDLRRDDDUDRLRUDRDLRRUDLUDRULDDUDLRLDDLDRLRDLULRDRULRLLRLUDUURULLLDDUULUUDDDUDRRULDDDULRUDRRLRLLLUDLULDUUULDDULDUUDLUULRDLDUDRUDLLDLDLLULDDDDLUDDUDRUDLRRRDDDDDLLRRDRUUDDDRRULRUDUUDRULLDLLLDDRDDUURLUUURUDRUDURLRUUUULUUURDRRRULDUULDLDDDRDDDDLLDRUDRDURLDDURDURULDDRLLRRLDUDRDURRLDRDLLULUUUD",
                    "LDDLRLRDDRLRUDDRDDUDRULUUULULDULRUULLRRDUULRDUUDDDRRULDDUDRLLLDULURDLDDRLLRURULULDLDULRDLDLRULUDLLDRUDLDURRDULDDRLRURDLLUDRDDDUDLUDULURULRDRLRULDLLRLDRRUDRDRUDRLDLRLUUURURRRLDDULLULLLRLRLULDLLRLDDRLDULURULRUURRUUURRUDRLRRURURDDDRULDULDLDLRRRLLDDRRURRULULULDRDULDRRULDUDRRLDULDRDURRDULLRRRLLLLRRLLRRRDRURDUULLURURURDDRRDRLLLULRRRDRLDRLDRDLLRUUDURRDRRDLLUDLDRLRLDLUDRDULRULRRLLRDLULDRLUDUUULLDRULDDLLRDUUUDRUUUUULUURDDLLDUURURRURLLURRDDUDUDRUUDDRDDRRLRLULRLRRRDRLLRRLLLDUULLUUDDLULLLDURRLLDRLDRDRLRRLRRULRRRRLRRRRRURUDULUULRDLLDRLRRDUURDRRUDRURRRDDRLDDLRLUDRDRDRRLDDDRDDRRRDUDULRURRDRDLLDRUD",
                    "UUUDLDDLRDLLLLRUUURDDLLURRUUURLUULLURUUDUDLDULULLRRRRLLLRDLLUDRUURDRURUDRURRLRLDRURLUDRLULRRURDDDURLLDULDLRRRDUUDDDRDLRUURRDRDRLRDLULRLDDRULRULDRDUDRUURLDLUDDULLLRURRLURLULDRRLUUURURLDLDDULLLRUUURDDDUURULULLUUUDUDRLLRRULUULDDDLLUDLURLLLRRULLURDRLUUDDLLDLLLUDULLRDRRRURDRUDUDUULUDURDLRUDLLRDDRURUDURLRULURDDURULLRDDRLRRDRLLULRDDDULRDLRULDDLRRDULDLUURRURUULRRDUURUDRRRRRLDULDLRURRULULDLRDDDRLLDURRULDUDUDRRRLUULRLUDURRRLRLDURRRRUULDRLUDDDUDURLURUDLLUDRDDDRLLURLRLDDURUUDDDUDUR",
                    "RURRRRURUDDRLURUDULRDUDDDUURULDRRRRURDLDRRLLDLUDLRRLRRUULLURULLRDLLRDDDDULLRLLDDLLRUDDULDUDLDURLRUULDDURURDURDLDRRULRURRRRRLRRLLUDURRURULRLRDLRLRRRLLURURDLLLDLDDULDLUDDLLLRUDDRDRLRUDRRLDDLRDLRLRLRLRRDUUURRUDRRLDLRRUULULLUDRRRUDLURDRUULDRDRRLUULULDDLURRLDULLURLDRLDULDRLLDLUUULLULRRDDRURRURLDLDRRLLLLLUDUURUULURLRDDDLRRRRLLLURUDLDDRDDRRUDURUULDRRULLLRRLRULLLRLDDLLRRLRURLRDRUDULLDDLDDDDDLDURURDLULRDDLRDLLRURLLRDLRUDDRDRRDURDURLUDRLDUDDDRRURRLUULURULLRLRDLRRLRURULLDDURLLRRRUDDRDLULURRRUUUULUULRRLLDLRUUURLLURLUURRLRL"
                };

        [Fact]
        public void Day2Part1()
        {
            var code = GetCode(input, keypad1);
            output.WriteLine(code);
        }

        Keypad keypad2 = new Keypad(new char?[,] {
                { null, null, '1', null, null},
                { null, '2', '3', '4', null},
                { '5', '6', '7', '8', '9'},
                { null, 'A', 'B', 'C', null},
                { null, null, 'D', null, null},
            }, new Coordinate(2, 0));



        [Fact]
        public void Day2Part2()
        {
            var code = GetCode(input, keypad2);
            output.WriteLine(code);
        }


        [Fact]
        public void Draft()
        {
            char?[,] keys = new char?[,]
            {
                {'1','2','3'},
                {'4','5','6'},
                {'7','8','9'}
            };

            (int row, int column) = (0, 0);
            output.WriteLine(keys[row, column].ToString());
            (row, column) = (1, 0);
            output.WriteLine(keys[row, column].ToString());
            (row, column) = (0, 1);
            output.WriteLine(keys[row, column].ToString());



        }

        private static string GetCode(string[] inputs, Keypad keypad)
        {
            var sb = new StringBuilder();
            foreach (var input in inputs)
            {
                foreach (var c in input)
                {
                    keypad.Move(c);
                }
                sb.Append(keypad.Current);
            }
            return sb.ToString();
        }


    }
}