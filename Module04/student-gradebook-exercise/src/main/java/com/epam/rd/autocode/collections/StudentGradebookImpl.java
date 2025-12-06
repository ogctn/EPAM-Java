package com.epam.rd.autocode.collections;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


public class StudentGradebookImpl implements StudentGradebook{

	private Map<Student, Map<String, BigDecimal>> map;
    private Comparator<Student> comparator;

	public StudentGradebookImpl() {
        comparator = new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                int cmp = o1.getFirstName().compareTo(o2.getFirstName());
                if (cmp != 0)
                    return (cmp);
                cmp = o1.getLastName().compareTo(o2.getLastName());
                if (cmp != 0)
                    return (cmp);
                return (o1.getGroup().compareTo(o2.getGroup()));
            }
        };
        map = new TreeMap<>(comparator);
	}

	@Override
	public boolean addEntryOfStudent(Student student, String discipline, BigDecimal grade) {
		if (student == null || discipline == null || grade == null)
            throw (new NullPointerException());

        Map<String, BigDecimal> grades = map.get(student);
        if (grades == null) {
            grades = new HashMap<>();
            map.put(student, grades);
        }

        BigDecimal exists = grades.get(discipline);
        if (exists != null && exists.compareTo(grade) == 0)
            return (false);

        grades.put(discipline, grade);
        return (true);
	}

	@Override
	public int size() {
        return (map.size());
	}

	@Override
	public Comparator<Student> getComparator() {
		return (comparator);
	}

	@Override
	public List<String> getStudentsByDiscipline(String discipline) {
		if (discipline == null)
            throw (new NullPointerException());

        List<String> list = new ArrayList<>();
        for (Map.Entry<Student, Map<String, BigDecimal>> entry : map.entrySet()) {
            Map<String, BigDecimal> grades = entry.getValue();
            if (grades.containsKey(discipline))
                list.add(entry.getKey().getFirstName() + "_" + entry.getKey().getLastName() +
                        ": " + grades.get(discipline));
        }
        return (list);
	}

	@Override
	public Map<Student, Map<String, BigDecimal>> removeStudentsByGrade(BigDecimal grade) {
        if (grade == null)
            throw (new NullPointerException());

        TreeMap<Student, Map<String, BigDecimal>> removed = new TreeMap<>(comparator);
        Iterator<Map.Entry<Student, Map<String, BigDecimal>>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Student, Map<String, BigDecimal>> entry = it.next();
            Collection<BigDecimal> grades = entry.getValue().values();
            for (BigDecimal g : grades) {
                if (g.compareTo(grade) < 0) {
                    removed.put(entry.getKey(), entry.getValue());
                    it.remove();
                    break;
                }
            }
        }
        return (removed);
    }

	@Override
	public Map<BigDecimal, List<Student>> getAndSortAllStudents() {
        TreeMap<BigDecimal, List<Student>> sorted = new TreeMap<>(Collections.reverseOrder());
	    for (Map.Entry<Student, Map<String, BigDecimal>> entry : map.entrySet()) {
            Collection<BigDecimal> grades = entry.getValue().values();
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal g : grades)
                sum = sum.add(g);
            BigDecimal avg = sum.divide(new BigDecimal(grades.size()),1, RoundingMode.HALF_UP);
            List<Student> students = sorted.getOrDefault(avg, new ArrayList<>());
            students.add(entry.getKey());
            sorted.put(avg, students);
        }
        return (sorted);
    }
	
}
