package com.annwyn.niflheim.generator;

import com.annwyn.niflheim.configuration.properties.NiflheimProperties;
import com.annwyn.niflheim.generator.configure.DatabaseQueryExecute;
import com.annwyn.niflheim.generator.models.TableEntity;
import com.annwyn.niflheim.generator.transfer.TransferContext;
import com.annwyn.niflheim.generator.transfer.TransferContextFactory;
import com.annwyn.niflheim.generator.transfer.TransferProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Slf4j
@Component
public class NiflheimProcessor {

	@Resource
	private DatabaseQueryExecute databaseQueryExecute;

	@Resource
	private NiflheimProperties niflheimProperties;

	@Resource
	private TransferContextFactory transferContextFactory;

	@Resource
	private List<TransferProcess> transferProcesses;

	public void startProcessor() throws Exception {
		log.info("开始生成实体类. ");
		Path outputDirectory = Paths.get(this.niflheimProperties.getOutputDirectory());
		if (this.niflheimProperties.isCleanPathAtStartup() && Files.exists(outputDirectory)) {
//			Files.walk(Paths.get(this.niflheimProperties.getOutputDirectory())) //
//				.filter(item -> !Files.isDirectory(item)) //
//				.forEach(item -> { //
//					if (Files.isDirectory(item)) {
//						try {
//							Files.deleteIfExists(item);
//						} catch (IOException e) {
//							log.error(String.format("文件: [%s] 删除失败", item), e);
//						}
//					}
//				});
			FileSystemUtils.deleteRecursively(outputDirectory.toFile());
		}

		// 扫描数据库
		List<TableEntity> tableEntities = this.databaseQueryExecute.queryTables();
		log.info("扫描数据库完成, 一共检索到{}张表", tableEntities.size());

		// 生成文件信息
		for (TableEntity tableEntity : tableEntities) {
			TransferContext transferContext = this.transferContextFactory.buildTransferContext(tableEntity);
			for (TransferProcess transferProcess : transferProcesses) {
				transferProcess.writeFile(transferContext);
			}
		}
	}


}
