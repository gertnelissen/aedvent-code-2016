using System;
using System.Numerics;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Puzzle9b;

namespace Puzzle9b.Test
{
    [TestClass]
    public class UnitTest1
    {
        private Calculator _calculator;

        [TestInitialize]
        public void Init()
        {
            _calculator = new Calculator();
        }

        private void assert(BigInteger length, string input)
        {
            Assert.AreEqual(length, _calculator.Calculate(input));
        }

        [TestMethod]
        public void TestMethod1()
        {
            var x = "(3x3)XYZ";
            assert(9, x);
        }
        [TestMethod]
        public void TestMethod2()
        {
            var x = "X(8x2)(3x3)ABCY";
            assert(20, x);
        }

        [TestMethod]
        public void TestMethod3()
        {
            var x = "(27x12)(20x12)(13x14)(7x10)(1x12)A";
            assert(241920, x);
        }

        [TestMethod]
        public void TestMethod4()
        {
            var x = "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN";
            assert(445, x);
        }
    }
}
