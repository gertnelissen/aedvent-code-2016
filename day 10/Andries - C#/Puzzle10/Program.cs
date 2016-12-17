using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Puzzle10
{
    class Program
    {
        static void Main(string[] args)
        {
            string line;
            List<string> rawInstructions = new List<string>();

            // Read the file and display it line by line.
            System.IO.StreamReader file =
               new System.IO.StreamReader(args[0]);
            while ((line = file.ReadLine()) != null)
            {
                rawInstructions.Add(line);
            }
            file.Close();

            // run program
            var botFactory = new BotFactory(rawInstructions);
            botFactory.RunFactory();


            // Suspend the screen.
            Console.ReadLine();
        }
    }
}
