package com.epam.rd.autocode.set;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.ServiceLoader;
import java.util.Set;

public class Member {
	
	private String name;
	private Level level;
	private Set<Skill> skills;

	public Member(String name, Level level, Skill... skills) {
        this.name = name;
        this.level = level;
        this.skills = EnumSet.noneOf(Skill.class);

        if (skills != null) {
            for (Skill sk : skills) {
                if (sk != null)
                    this.skills.add(sk);
            }
        }
    }

	public String getName() {
		return (name);
	}
	
	public Level getLevel() {
		return (level);
	}
	
	public Set<Skill> getSkills() {
		return (EnumSet.copyOf(skills));
	}

}
