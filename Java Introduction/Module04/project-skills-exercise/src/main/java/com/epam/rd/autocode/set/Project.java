package com.epam.rd.autocode.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class Project {

    private final ArrayList<Role> roles;

	private static class Entry {
        private final Level level;
        private final Skill skill;

        Entry(Level level, Skill skill) {
            this.level = level;
            this.skill = skill;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return (true);
            if (!(o instanceof Entry))
                return (false);
            Entry other = (Entry) o;
            return (this.level == other.level &&
                    this.skill == other.skill);
        }

        @Override
        public int hashCode() {
            return (level.hashCode() * 31 + skill.hashCode());
        }
    }

	public Project(Role... roles) {
        this.roles = new ArrayList<Role>();
        if (roles != null) {
            for (Role r : roles) {
                if (r != null)
                    this.roles.add(r);
            }
        }
	}

    public java.util.List<Role> getRoles() {
        return (new ArrayList<>(roles));
    }

	public int getConformity(Set<Member> team) {
		ArrayList<Entry> projects = new ArrayList<Entry>();
        for (Role r : roles) {
            for (Skill s : r.getSkills())
                projects.add(new Entry(r.getLevel(), s));
        }

        if (projects.isEmpty())
            return (0);
        int k = projects.size();

        ArrayList<Entry> teams = new ArrayList<Entry>();
        for (Member m : team) {
            for (Skill s : m.getSkills())
                teams.add(new Entry(m.getLevel(), s));
        }

        ArrayList<Entry> removeProjects = new ArrayList<Entry>();
        ArrayList<Entry> removeTeam = new ArrayList<Entry>();
        for (int i = 0; i < projects.size(); i++) {
            Entry eProject = projects.get(i);
            for (int j = 0; j < teams.size(); j++) {
                Entry eTeam = teams.get(j);
                if (eProject.equals(eTeam)) {
                    projects.remove(i);
                    teams.remove(j);
                    i--;
                    break;
                }
            }
        }
        int match = k - projects.size();
        return ((match * 100) / k);
	}
	
}
