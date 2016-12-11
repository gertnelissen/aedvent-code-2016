using System;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using Xunit;
using Xunit.Abstractions;

namespace Jeroen
{

    public class Tests
    {
        ITestOutputHelper output;

        public Tests(ITestOutputHelper output)
        {
            this.output = output;
        }

        MD5 _md5 = MD5.Create();

        [Theory]
        [InlineData("abc0", false)]
        [InlineData("abc1", false)]
        [InlineData("abc3231929", true)]
        [InlineData("abc5017308", true)]
        [InlineData("abc5278568", true)]
        public void TestHashValidation(string input, bool expected)
        {
            var cracker = new Cracker();
            var hash = _md5.ComputeHash(Encoding.ASCII.GetBytes(input));
            var isValid = cracker.StartsWith5Zeroes(hash);
            Assert.Equal(expected, isValid);
        }

        [Fact]
        public void TestPart1()
        {
            var cracker = new Cracker();
            var password = cracker.GeneratePassword("abc", 8);
            Assert.Equal("18f47a30", password);
        }



        public void Part1()
        {
            var doorid = "ugkcyxxp";

        }

        [Theory]
        [InlineData(0, 0, 0, 0, true)]
        [InlineData(0, 0, 0, 0xFF, true)]
        [InlineData(0, 0, 0x0F, 0xFF, true)]
        [InlineData(0, 0, 0x1F, 0xFF, false)]
        public void ByteArrayStartsWith5ZeroesTest(byte a, byte b, byte c, byte d, bool expected)
        {
            var cracker = new Cracker();
            var result = cracker.StartsWith5Zeroes(new[] { a, b, c, d });
            Assert.Equal(expected, result);
        }

    }

    class Cracker
    {
        MD5 _md5 = MD5.Create();
        public bool StartsWith5Zeroes(byte[] hash)
        {
            //return BitConverter.ToString(hash).Replace("-", "").StartsWith("00000");
            return hash[0] == 0 && hash[1] == 0 && (hash[2] & 0x0F) == hash[2];
        }

        internal string GeneratePassword(string input, int length)
        {
            StringBuilder sb = new StringBuilder();
            var doorid = input;
            int i = 0;
            while (true)
            {
                var s = $"{doorid}{i}";
                var hash = _md5.ComputeHash(Encoding.ASCII.GetBytes(s));
                if (StartsWith5Zeroes(hash))
                {
                    var str = BitConverter.ToString(hash).Replace("-", "");
                    sb.Append(str[5]);
                    Console.Write(str[5]);
                }
                if (sb.Length == length)
                    break;
                i++;
            }
            var password = sb.ToString();
            return password;
        }
    }

    public static class Program
    {
        public static void Main()
        {
            var cracker = new Cracker();
            cracker.GeneratePassword("ugkcyxxp", 8);
        }
    }
}
