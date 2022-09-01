package gitlet;

import java.util.*;
import java.io.Serializable;

/** @SOURCE Adapted from Lab 15: k-D Trees and Trie, Berkeley CS61BL Summer 2020*/

public class Trie implements Serializable {
    private Node root = new Node();

    private class Node {
        private boolean isKey;
        private HashMap<Character, Node> map = new HashMap<Character, Node>();
        private Node prev = null;

        private Node(Character c, Boolean key, Node previous) {
            isKey = key;
            map.put(c, new Node());
            prev = previous;
        }

        private Node() {
            isKey = false;
        }
    }

    public Trie() {
        root = new Node();
    }

    public void clear() { root.map = new HashMap<Character, Node>(); }

    public boolean contains(String key) {
        Node curr = root;
        for (int i = 0; i < key.length(); i++) {
            if (curr == null) {
                return false;
            }
            Character c = key.charAt(i);
            if (curr.map.containsKey(c)) {
                curr = curr.map.get(c);
            } else {
                return false;
            }
        }
        if (curr.isKey) {
            return true;
        }
        return false;
    }

    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false, curr));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    public List<String> keysWithPrefix(String prefix) {
        ArrayList<String> results = new ArrayList<String>();
        Node curr = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (!curr.map.containsKey(c)) {
                return results;
            }
            curr = curr.map.get(c);
        }
        prefixHelper(curr, prefix, results);
        return results;
    }

    private void prefixHelper(Node curr, String word, List<String> results) {
        if (curr.map.keySet().isEmpty()) {
            return;
        }
        if (curr.isKey) {
            results.add(word);
        }
        for (char c : curr.map.keySet()) {
            String newWord = word + c;
            prefixHelper(curr.map.get(c), newWord, results);
        }
    }
}

