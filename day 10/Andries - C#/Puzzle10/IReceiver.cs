using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Puzzle10
{
    public interface IReceiver
    {
        int Id { get; }
        void InsertChip(int chipNumber);
        bool CanInsertChip();
    }

}
