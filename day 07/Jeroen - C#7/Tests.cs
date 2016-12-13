using System.Linq;
using System.Text.RegularExpressions;
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

        enum State
        {
            Accumulating
        }


        [Theory]
        [InlineData("abba[mnop]qrst", true)]
        [InlineData("abcd[bddb]xyyx", false)]
        [InlineData("aaaa[qwer]tyui", false)]
        [InlineData("ioxxoj[asdfgh]zxcvbn", true)]
        [InlineData("ioxxoj[asdfgh]zxcvbnioxxoj[asdfgh]zxcvbn", true)]
        [InlineData("ioxxoj[asdfgh]zxcvbnioxxoj[asabba]zxcvbn", false)]
        public void Specs(string input, bool expected)
        {
            Assert.Equal(expected, new IPAddress(input).SupportsTLS());
        }


    }

    struct IPAddress
    {
        string input;

        public IPAddress(string input)
        {
            this.input = input;
        }

        enum WhereAmI
        {
            Outside,
            Inside
        }
        public bool SupportsTLS()
        {
            var whereami = WhereAmI.Outside;

            bool atLeastOnePalindrome = false;
            var startIndex = 0;
            for (int i = 0; i < input.Length; i++)
            {
                var c = input[i];
                switch (whereami)
                {
                    case WhereAmI.Outside when c == '[':
                        whereami = WhereAmI.Inside;
                        startIndex = i + 1;
                        break;
                    case WhereAmI.Outside when !atLeastOnePalindrome && i >= startIndex + 3:
                        atLeastOnePalindrome = IsPalindrome(i);
                        break;
                    case WhereAmI.Inside when c == ']':
                        whereami = WhereAmI.Outside;
                        startIndex = i + 1;
                        break;
                    case WhereAmI.Inside when i >= startIndex + 3:
                        if (IsPalindrome(i)) return false;
                        break;
                }
            }
            return atLeastOnePalindrome;
        }

        private bool IsPalindrome(int i)
        {
            return (
                input[i] == input[i - 3] && input[i - 1] == input[i - 2] && input[i - 1] != input[i]
                );
        }
    }
}
