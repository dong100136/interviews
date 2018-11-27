#include <iostream>
using namespace std;

enum COLOR
{
    RED,
    BLACK
};

class TreeNode
{
  public:
    string key;
    string value;
    int size;
    TreeNode *left, *right;
    COLOR color;
};

class RedBlackBST
{
  public:
    void put(string key, string val);
    string get(string key);

  private:
    TreeNode *root;
    TreeNode put(TreeNode *h, string key, string value);
    TreeNode rotateRight(TreeNode *h);
    TreeNode rotateLeft(TreeNode *h);
    TreeNode flipColor(TreeNode *h);
    bool isRed(TreeNode *h);
};


