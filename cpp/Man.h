#include <string>
using namespace std;

class Man
{
  public:
    Man(string name)
    {
        this->name = name;
    }

    string &getName();

  private:
    string name;
};