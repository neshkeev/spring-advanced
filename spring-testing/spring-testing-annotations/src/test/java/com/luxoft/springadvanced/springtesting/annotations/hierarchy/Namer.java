package com.luxoft.springadvanced.springtesting.annotations.hierarchy;

interface Namer {
    String getName();

    class ParentNamer implements Namer {

        @Override
        public String getName() {
            return "Parent";
        }
    }

    class ChildNamer implements Namer {

        @Override
        public String getName() {
            return "Child";
        }
    }

    class HeirNamer implements Namer {

        @Override
        public String getName() {
            return "Heir";
        }
    }
}
